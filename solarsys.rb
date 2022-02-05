# interface graphique en Jruby

include Java

require "./cinnamon/ObjectData.rb"
require "./cinnamon/Calculs.rb"

#MEMO :
# class CelestialBody
# :name, :referenceFrame, :position, :diameter, :periodOfRotation, :periodOfRevolution, :distanceFromOrigin, :position

################################################################################################
######################################DEFINITIONS###############################################

bodyDir = "bodies"

def jsonToCelestialBody(json, aroundWhat)
    name = json["englishName"]
    referenceFrame = aroundWhat
    diameter = 2*json["meanRadius"]
    avgDistFromOrigin =  (json["perihelion"] + json["aphelion"])/2
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
    CelestialBody.new(name,referenceFrame,diameter,avgDistFromOrigin,revolutionPeriod,periodOfRotation,position)
end

#######################################################################################################################
#######################################################################################################################
########################################GENERATING THE DATA FOR JAVA###################################################

sourceBody = CelestialBody.new("Sun",nil, 1392684, 0 , 220e6 , 27*24 ,0)

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

p sourceBody

# $window = Java::vanilla.view.Window.new(sourceBody)