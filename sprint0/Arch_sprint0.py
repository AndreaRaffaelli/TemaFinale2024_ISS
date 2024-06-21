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
          sonar=Custom('sonar','./qakicons/symActorSmall.png')
          wastestorage=Custom('wastestorage','./qakicons/symActorSmall.png')
          oprobot=Custom('oprobot','./qakicons/symActorSmall.png')
          incinerator=Custom('incinerator','./qakicons/symActorSmall.png')
          mdevice=Custom('mdevice','./qakicons/symActorSmall.png')
     sys >> Edge( label='sonarUpdate', **evattr, decorate='true', fontcolor='darkgreen') >> wis
     sys >> Edge( label='scaleUpdate', **evattr, decorate='true', fontcolor='darkgreen') >> wis
     wastestorage >> Edge(color='blue', style='solid',  decorate='true', label='<scaleUpdate &nbsp; >',  fontcolor='blue') >> wis
     sonar >> Edge(color='blue', style='solid',  decorate='true', label='<sonarUpdate &nbsp; >',  fontcolor='blue') >> wis
diag
