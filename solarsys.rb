# interface graphique en Jruby

include Java

require "./cinnamon/CelestialBody.rb"
require "./cinnamon/Calculs.rb"

#MEMO :
# class CelestialBody
# :name, :referenceFrame, :position, :diameter, :periodOfRotation, :periodOfRevolution, :distanceFromOrigin, :position

################################################################################################
######################################DEFINITIONS###############################################

bodyDir = "bodies"

def jsonToCelestialBody(json, aroundWhat)
    name = json["englishName"]
    referenceFrame = aroundWhat.name
    diameter = 2*json["meanRadius"]
    revolutionPeriod = json["sideralOrbit"]
    periodOfRotation = json["sideralRotation"]
    position = KeplerCoord.new(
        json["semimajorAxis"],
        json["eccentricity"],
        json["inclination"],
        json["longAscNode"],
        json["argPeriapsis"],
        json["mainAnomaly"]
    )
    position = position.toCartesian aroundWhat
    avgDistFromOrigin =  Math.sqrt(position.x*position.x+position.y*position.y)
    CelestialBody.new(name,referenceFrame,diameter,avgDistFromOrigin,revolutionPeriod,periodOfRotation,position)
end

#######################################################################################################################
#######################################################################################################################
########################################GENERATING THE DATA FOR JAVA###################################################

sourceBody = CelestialBody.new("Sun",nil, 1392684, 0 , 220e6 , 27*24 , Coords.new())

entries = Dir.entries(bodyDir)
for entry in entries
    if entry != "." && entry != ".."

        file = File.open(bodyDir + "/" + entry + "/" +  entry + ".json" )
        file_data = file.read
        struct_data = JSON.parse(file_data)

        currentPlanet = jsonToCelestialBody(struct_data, sourceBody)
        moonEntries = Dir.entries(bodyDir + "/" + entry + "/moon")
        for moon in moonEntries
            if moon != "." && moon != ".."
                file = File.open(bodyDir + "/" + entry + "/moon/" +  moon )
                file_data = file.read
                struct_data = JSON.parse(file_data)
                currentMoon = jsonToCelestialBody(struct_data,currentPlanet)
                currentPlanet.addSatellite(currentMoon)
            end
        end
        sourceBody.addSatellite(currentPlanet)
    end
end

JsourceBody = Java::vanilla.model.CelestialBody.new(sourceBody.name,nil, Java::vanilla.model.SpacePoint.new(0,0), sourceBody.diameter, 1, sourceBody.periodOfRevolution, sourceBody.periodOfRotation, sourceBody.distanceFromOrigin)

sourceBody.satelliteList.each do |thebody|
    JsourceBody.jAddSatellite(thebody.to_java()) 
end

$window = Java::vanilla.view.Window.new(JsourceBody)
