### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
evattr = {
    'color': 'darkgreen',
    'style': 'dotted'
}
with Diagram('testoprobotArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxtest', graph_attr=nodeattr):
          oprobot=Custom('oprobot','./qakicons/symActorSmall.png')
          wis=Custom('wis','./qakicons/symActorSmall.png')
          incinerator=Custom('incinerator','./qakicons/symActorSmall.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     sys >> Edge( label='burnEnd', **evattr, decorate='true', fontcolor='darkgreen') >> oprobot
     incinerator >> Edge( label='burnEnd', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     oprobot >> Edge(color='magenta', style='solid', decorate='true', label='<engage &nbsp; moverobot<font color="darkgreen"> moverobotdone moverobotfailed</font> &nbsp; >',  fontcolor='magenta') >> basicrobot
     oprobot >> Edge(color='blue', style='solid',  decorate='true', label='<startBurn &nbsp; >',  fontcolor='blue') >> incinerator
     oprobot >> Edge(color='blue', style='solid',  decorate='true', label='<robotUpdate &nbsp; >',  fontcolor='blue') >> wis
diag
