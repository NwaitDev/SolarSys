# interface graphique en Jruby

include Java

$window = Java::vanilla.view.Window.new()

$window.getPanels.get(1).addBody("hello you !")