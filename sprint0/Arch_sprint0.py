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
with Diagram('sprint0Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxLocale', graph_attr=nodeattr):
          wis=Custom('wis','./qakicons/symActorSmall.png')
          oprobot=Custom('oprobot','./qakicons/symActorSmall.png')
          incinerator=Custom('incinerator','./qakicons/symActorSmall.png')
          sonar=Custom('sonar','./qakicons/symActorSmall.png')
          wastestorage=Custom('wastestorage','./qakicons/symActorSmall.png')
          monitoringdevice=Custom('monitoringdevice','./qakicons/symActorSmall.png')
     sys >> Edge( label='burnEnd', **evattr, decorate='true', fontcolor='darkgreen') >> wis
     sys >> Edge( label='burnEnd', **evattr, decorate='true', fontcolor='darkgreen') >> oprobot
     incinerator >> Edge( label='burnEnd', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sonar >> Edge(color='blue', style='solid',  decorate='true', label='<sonarUpdate &nbsp; >',  fontcolor='blue') >> wis
     oprobot >> Edge(color='blue', style='solid',  decorate='true', label='<depositRP &nbsp; >',  fontcolor='blue') >> wis
     oprobot >> Edge(color='blue', style='solid',  decorate='true', label='<addAsh &nbsp; >',  fontcolor='blue') >> sonar
     wis >> Edge(color='blue', style='solid',  decorate='true', label='<ledOn &nbsp; ledOff &nbsp; >',  fontcolor='blue') >> monitoringdevice
     wis >> Edge(color='blue', style='solid',  decorate='true', label='<burnStart &nbsp; >',  fontcolor='blue') >> incinerator
     sonar >> Edge(color='blue', style='solid',  decorate='true', label='<ledBlink &nbsp; ledOff &nbsp; >',  fontcolor='blue') >> monitoringdevice
     wastestorage >> Edge(color='blue', style='solid',  decorate='true', label='<scaleUpdate &nbsp; >',  fontcolor='blue') >> wis
     wis >> Edge(color='blue', style='solid',  decorate='true', label='<robotStart &nbsp; >',  fontcolor='blue') >> oprobot
     oprobot >> Edge(color='blue', style='solid',  decorate='true', label='<decScale &nbsp; >',  fontcolor='blue') >> wastestorage
diag
