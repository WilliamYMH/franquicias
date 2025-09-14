from diagrams import Diagram, Cluster, Edge
from diagrams.onprem.client import Users
from diagrams.onprem.compute import Server
from diagrams.onprem.database import Mysql

# Genera docs/architecture/app.png
with Diagram(
    "Franquicias - Hexagonal Architecture",
    filename="app",
    outformat="png",
    show=False,
    graph_attr={"rankdir": "LR", "splines": "spline", "pad": "0.5", "nodesep": "0.7", "ranksep": "1.0"},
):
    users = Users("Usuarios\n(Navegador / REST Client)")

    # Lado izquierdo del hexágono: Adapters de entrada (Inbound)
    with Cluster("Hexagon · Inbound Adapters (Driving)"):
        c_f = Server("FranquiciaController")
        c_s = Server("SucursalController")
        c_p = Server("ProductoController")

    # Centro del hexágono: Núcleo (Domino + Aplicación)
    with Cluster("Hexagon · Core (Domain + Application)"):
        with Cluster("Input Ports (Use Cases)"):
            port_in_f = Server("FranquiciaUseCase")
            port_in_s = Server("SucursalUseCase")
            port_in_p = Server("ProductoUseCase")

        with Cluster("Application Services (Implementations)"):
            s_f = Server("FranquiciaService")
            s_s = Server("SucursalService")
            s_p = Server("ProductoService")

        with Cluster("Output Ports (Driven)"):
            port_out_f = Server("FranquiciaRepository")
            port_out_s = Server("SucursalRepository")
            port_out_p = Server("ProductoRepository")

    # Lado derecho del hexágono: Adapters de salida (Outbound)
    with Cluster("Hexagon · Outbound Adapters (Driven)"):
        a_f = Server("R2DBC Adapter: Franquicia")
        a_s = Server("R2DBC Adapter: Sucursal")
        a_p = Server("R2DBC Adapter: Producto")

    with Cluster("External Systems"):
        db = Mysql("MySQL (RDS)")

    # Flujo
    # Usuarios -> Inbound Adapters
    users >> Edge(label="HTTP/JSON") >> [c_f, c_s, c_p]

    # Inbound Adapters -> Input Ports (Core)
    c_f >> Edge(label="calls") >> port_in_f
    c_s >> Edge(label="calls") >> port_in_s
    c_p >> Edge(label="calls") >> port_in_p

    # Input Ports -> Services (Core)
    port_in_f >> Edge(label="delegates") >> s_f
    port_in_s >> Edge(label="delegates") >> s_s
    port_in_p >> Edge(label="delegates") >> s_p

    # Services -> Output Ports (Core)
    s_f >> Edge(label="uses") >> port_out_f
    s_s >> Edge(label="uses") >> port_out_s
    s_p >> Edge(label="uses") >> port_out_p

    # Output Ports -> Outbound Adapters
    port_out_f >> Edge(label="implemented by") >> a_f
    port_out_s >> Edge(label="implemented by") >> a_s
    port_out_p >> Edge(label="implemented by") >> a_p

    # Outbound Adapters -> External Systems
    [a_f, a_s, a_p] >> Edge(label="R2DBC") >> db
