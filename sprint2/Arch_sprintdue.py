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
with Diagram('sprintdueArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxservicearea', graph_attr=nodeattr):
          wis=Custom('wis(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxashstorage', graph_attr=nodeattr):
          monitoring_device=Custom('monitoring_device','./qakicons/symActorWithobjSmall.png')
          sonardevice=Custom('sonardevice','./qakicons/symActorSmall.png')
          datacleaner=Custom('datacleaner','./qakicons/symActorSmall.png')
     sys >> Edge( label='startBurn', **evattr, decorate='true', fontcolor='darkgreen') >> monitoring_device
     sys >> Edge( label='endBurn', **evattr, decorate='true', fontcolor='darkgreen') >> monitoring_device
     monitoring_device >> Edge( label='sonarUpdate', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sonardevice >> Edge( label='sonardata', **eventedgeattr, decorate='true', fontcolor='red') >> datacleaner
     datacleaner >> Edge(color='blue', style='solid',  decorate='true', label='<info &nbsp; >',  fontcolor='blue') >> monitoring_device
diag
