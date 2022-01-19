# interface graphique en Jruby

include Java

require "./cinnamon/ObjectData.rb"
require "./cinnamon/Calculs.rb"

#MEMO :
# class CelestialBody
# :name, :referenceFrame, :position, :diameter, :scale, :periodOfRotation, :periodOfRevolution, :distanceFromOrigin, :clockwise

# class SolarSystem 
# :sunList, :planetList



solarSystem = SolarSystem.new("bodies")
solarSystem.sunList.each do |elem|
    p elem.name 
end

solarSystem.planetList.each do |elem|
    p elem.name 
end

jSolarSystem = Java::vanilla.model.SolarSystem.new()

solarSystem.sunList.each do |body|
    jSolarSystem.addSun(body.java_Celestialbody)    
end
solarSystem.planetList.each do |body|
    jSolarSystem.addPlanet(body.java_Celestialbody)  
end

$window = Java::vanilla.view.Window.new(jSolarSystem)