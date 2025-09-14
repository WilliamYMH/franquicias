from diagrams import Diagram, Cluster, Edge
from diagrams.aws.network import VPC, PublicSubnet, PrivateSubnet, InternetGateway, RouteTable
from diagrams.aws.compute import EC2
from diagrams.aws.database import RDS
from diagrams.aws.general import InternetAlt1

# Genera docs/architecture/cloud.png
with Diagram(
    "Franquicias - Cloud Architecture",
    filename="cloud",
    outformat="png",
    show=False,
    graph_attr={"rankdir": "LR", "splines": "spline", "pad": "0.5", "nodesep": "0.6", "ranksep": "0.9"},
):
    internet = InternetAlt1("Internet\n(Usuarios)")
    with Cluster("VPC 10.0.0.0/16"):
        igw = InternetGateway("Internet Gateway")
        rt_public = RouteTable("Public Route Table\n0.0.0.0/0 -> IGW")

        with Cluster("Public Subnet 10.0.1.0/24 (us-east-1a)"):
            public_subnet = PublicSubnet("Public Subnet")
            ec2 = EC2("EC2 App (Docker)\nSpring Boot WebFlux\nHost:80 → Cntnr:8080\nDocker Compose")

        with Cluster("DB Subnet Group (Private Subnets)"):
            with Cluster("Private Subnet 10.0.10.0/24 (us-east-1a)"):
                ps_a = PrivateSubnet("Private A")
            with Cluster("Private Subnet 10.0.11.0/24 (us-east-1b)"):
                ps_b = PrivateSubnet("Private B")
            rds = RDS("RDS MySQL\n(db.t3.micro)\nNot Publicly Accessible")

        # Flujo de tráfico y políticas de seguridad (anotadas en los bordes)
        internet >> Edge(label="HTTP 80\nSSH 22 (restringido)") >> igw
        igw >> Edge(label="Default Route\n0.0.0.0/0") >> rt_public
        rt_public >> Edge(label="Routed to\nPublic Subnet") >> public_subnet
        public_subnet >> ec2

        # Conectividad de aplicación a base de datos
        ec2 >> Edge(label="R2DBC:3306 (MySQL)\nPermitido solo desde SG de EC2") >> rds
