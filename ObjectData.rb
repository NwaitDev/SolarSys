require 'uri'
require 'net/http'
require 'json'



################################################
################################################

class CelestialBody
    attr_reader :name, :referenceFrame, :position, :diameter, :scale, :periodOfRotation, :periodOfRevolution, :distanceFromOrigin, :clockwise

    def initialize(name, referenceFrame, position, diameter, scale, distanceFromOrigin,periodOfRevolution, periodOfRotation, clockwise=false)  
        @name = name
        @referenceFrame = referenceFrame
        @position = position
        @diameter = diameter
        @scale = scale
        @periodOfRevolution = periodOfRevolution
        @periodOfRotation = periodOfRotation
        @distanceFromOrigin = distanceFromOrigin
        @clockwise = clockwise
    end
end

class Sun < CelestialBody
    def initialize
        super("Sun",self, [0,0], 1392684, 1 , 220e6 , 27*24 ,0 ,true)
    end
end


class Moon < CelestialBody

    def initialize(dirName , referenceName , moonName, referenceObject)
        moonName = moonName.gsub(" ", "_").gsub("/", "_BS_") 
        file = File.open(dirName + "/" + referenceName + "/moon/" +  moonName )
        file_data = file.read
        struct_data = JSON.parse(file_data)
        @name = struct_data["englishName"]
        @referenceFrame = referenceObject
        @diameter = 2* struct_data["meanRadius"]
        @scale = 1
        @revolutionPeriod = struct_data["sideralOrbit"]
        @periodOfRotation = struct_data["sideralRotation"]  
        @distanceFromOrigin =  (struct_data["perihelion"] +  struct_data["aphelion"])/2   
    end
end


class Planet < CelestialBody
    attr_reader :moonList

    @@sun = Sun.new()

    def getMoonByName(name)
        @moonList.detect do |m| m.name.upcase == name.upcase end
    end

    def getMoonNames
        names = []
        @moonList.each do | m | names.push(m.name) end
        names
    end

    def initialize(dirName , planetName)
        file = File.open(dirName + "/" + planetName + "/" +  planetName + ".json" )
        file_data = file.read
        struct_data = JSON.parse(file_data)
        @name = struct_data["englishName"]
        @referenceFrame = @@sun
        @diameter = 2* struct_data["meanRadius"]
        @scale = 1
        @revolutionPeriod = struct_data["sideralOrbit"]
        @periodOfRotation = struct_data["sideralRotation"]
        @moonList = []
        entries = Dir.entries(dirName + "/" + planetName + "/moon/" )
        @distanceFromOrigin =  (struct_data["perihelion"] +  struct_data["aphelion"])/2  
        for entry in entries
            if entry != "." && entry != ".."
                m_curr = Moon.new(dirName , planetName, entry, self)
                @moonList.push(m_curr)
            end 
        end
    end
end



class SolarSystem 
    attr_reader :sunList, :planetList

    def getPlanetNames()
        @planetList.inject([]) do |  list , p | list.push(p.name) end
    end

    def getSunNames()
        @planetList.inject([]) do | list , s| list.push(s.name) end
    end
    
    def getPlanetByName(name)
        planetList.detect do |p| p.name.upcase == name.upcase end
    end

    # return the diameter ratio between the two bodies
    def getRatioBetween(b1 , b2)
        b1.diameter / b2.diameter
    end

    def getAllRatios(planet)
        @planetList.inject([]) do  |list , pl | list.push({pl.name => pl.diameter / planet.diameter}) end
    end

    def getSunByName(name)
        sunList.detect do |s| s.name.upcase == name.upcase end
    end
        
    def initialize(bodyDir)
        @sunList = [Sun.new()]
        @planetList = []
        entries = Dir.entries(bodyDir)
        for entry in entries
            if entry != "." && entry != ".."
                curr_p = Planet.new(bodyDir , entry)
                @planetList.push(curr_p)
            end
        end
    end
end

s = SolarSystem.new("bodies")

p s.getPlanetNames()

earth = s.getPlanetByName("earth")



moon = earth.getMoonByName("moon")
mars = s.getPlanetByName("mars")
#p mars.getMoonNames

#p earth
#puts "\n\n\n"
#p moon


#puts "\n\n\n"

sun = s.getSunByName("sun")
p s.getAllRatios(sun)