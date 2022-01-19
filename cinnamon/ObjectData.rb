$LOAD_PATH.append("..")
require 'uri'
require 'net/http'
require 'json'
require "./cinnamon/Calculs.rb"
include Java



################################################
################################################

class CelestialBody
    attr_reader :name, :referenceFrame, :diameter, :scale, :periodOfRotation, :periodOfRevolution, :distanceFromOrigin, :clockwise, :position
    def initialize(name, referenceFrame, diameter, scale, distanceFromOrigin,periodOfRevolution, periodOfRotation, clockwise=false)  
        @name = name
        @referenceFrame = referenceFrame
        @position = Coords.new()
        @diameter = diameter
        @scale = scale
        @periodOfRevolution = periodOfRevolution
        @periodOfRotation = periodOfRotation
        @distanceFromOrigin = distanceFromOrigin
        @clockwise = clockwise
    end

    def setCartesianCoordinates(x,y,z,xSpeed,ySpeed,zSpeed)
        @position = CartesianCoord.new(x,y,z,xSpeed,ySpeed,zSpeed)
    end

    def setKeplerianCoordinates(semiMajorAxis, eccentricity, inclination, ascendingNodeAngle, periapsisArg, mainAnom)
        @position = KeplerCoord.new(semiMajorAxis, eccentricity, inclination, ascendingNodeAngle, periapsisArg, mainAnom)
    end

    def java_Celestialbody()
        if :cartesian == @position.type
            x = @position.x
            y = @position.y
            jpoint = Java::java.awt.Point.new(x.to_java(:int),y.to_java(:int))
        elsif :keplerian == @position.type
            currentPos = @position.toCartesian(@referenceFrame)
            x = currentPos.x
            y = currentPos.y
            jpoint = Java::java.awt.Point.new(x.to_java(:int),y.to_java(:int))
        else
            jpoint = nil.to_java
        end

        Java::vanilla.model.CelestialBody.new(
            @name.to_java(:String),
            jpoint,
            @diameter.to_java(:float),
            @scale.to_java(:float),
            @periodOfRevolution.to_java(:float),
            @periodOfRotation.to_java(:float),
            @distanceFromOrigin.to_java(:float),
            @clockwise.to_java
        )
    end
end

class Sun < CelestialBody
    def initialize
        super("Sun",nil, 1392684, 1 , 220e6 , 27*24 ,0 ,true)
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
        @distanceFromOrigin =  (struct_data["perihelion"] +  struct_data["aphelion"])/2  ###Not the real current distance from the sun yet

        setKeplerianCoordinates(
            struct_data["semimajorAxis"],
            struct_data["eccentricity"],
            struct_data["inclination"],
            struct_data["longAscNode"],
            struct_data["argPeriapsis"],
            struct_data["mainAnomaly"]
        )

        p @position
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