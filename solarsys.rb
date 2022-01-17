# interface graphique en Jruby

include Java

require "./cinnamon/ObjectData.rb"

$window = Java::vanilla.view.Window.new()

s = SolarSystem.new("bodies")

$window.getPanels.get(1).addBody("hello you !")