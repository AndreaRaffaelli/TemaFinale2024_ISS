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
with Diagram('ctx1Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctx1', graph_attr=nodeattr):
          prova1=Custom('prova1','./qakicons/symActorSmall.png')
     with Cluster('ctx2', graph_attr=nodeattr):
          prova2=Custom('prova2(ext)','./qakicons/externalQActor.png')
     sys >> Edge( label='evento', **evattr, decorate='true', fontcolor='darkgreen') >> prova1
     prova1 >> Edge(color='magenta', style='solid', decorate='true', label='<try &nbsp; >',  fontcolor='magenta') >> prova2
diag
