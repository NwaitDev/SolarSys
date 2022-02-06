# Calculations of the coordinates for each body of the solar system

def mu(referentialBody)
    case referentialBody.name
    when "Sun"
        1.32712440018 * (10**20)
    when "Mercury"
        2.2032 * (10**13)
    when "Venus"
        3.24859 * (10**14)
    when "Earth"
        3.986004418 * (10**14)
    when "Mars"
        4.282837 * (10**13)
    when "Jupiter"
        1.26686534 * (10**17)
    when "Saturn"
        3.7931187 * (10**16)
    when "Uranus"
        5.793939 * (10**15)
    when "Neptune"
        6.836529 * (10**15)
    when "Pluto"
        8.71 * (10**11)
    else
        throw :NoCorrespondingCelestialBody
    end
end


def eccentricAnomaly(meanAnomaly,eccentricity)
    meanAnomaly = meanAnomaly/360.0
    meanAnomaly = 2*Math::PI*(meanAnomaly-meanAnomaly.floor)

    def F(estimatedExcentricAnomaly,meanAnomaly)
        estimatedExcentricAnomaly-eccentricity*Math::sin(estimatedExcentricAnomaly)-meanAnomaly
    end

    def derOfF(estimatedExcentricAnomaly,eccentricity)
        1-eccentricity*Math::cos(estimatedExcentricAnomaly)
    end

    if (eccentricity < 0.8) 
        guess=meanAnomaly
    else 
        guess=Math::PI
    end
    precision =  0.0000001

    #def newtonsMethod(guess, precision, functionF, derivativeOfFunctionF)
    max_iteration = 30
    current_iteration = 0
    x = guess
    f = F(x,meanAnomaly)
    while(f.abs() > precision && current_iteration < max_iteration)
        x = x - (f / derOfF(x,eccentricity))
        f = F(x,meanAnomaly)
    end
    x
    #end
end

def trueAnomaly(eccentricAnomaly,eccentricity)    
    2*Math::atan2(Math::sqrt(1+eccentricity)*Math::sin(eccentricAnomaly/2),Math::sqrt(1-eccentricity)*Math::sin(eccentricAnomaly/2))
end

def distanceFromCentralBody(eccentricity,semiMajorAxis, eccentricAnomaly)
    semiMajorAxis*(1-eccentricity*Math::cos(eccentricAnomaly))
end

def orbitalFrameCartesianCoord(semiMajorAxis, trueAnomaly, distanceFromCentralBody,mu,eccentricAnomaly)
    factPos = distanceFromCentralBody
    x = factPos * Math::cos(trueAnomaly)
    y = factPos * Math::sin(trueAnomaly)
    z = 0

    factSpeed = Math::sqrt(mu*semiMajorAxis)/distanceFromCentralBody
    xSpeed = factSpeed * (-Math::sin(eccentricAnomaly))
    ySpeed = factSpeed * Math::sqrt(1-eccentricity**2)*Math::cos(eccentricAnomaly)
    zSpeed = 0

    CartesianCoord.new(x,y,z,xSpeed,ySpeed,zSpeed)
end

def bodyCentricFrameCartesianCoord(orbCoords,inclination,periapsisArg,ascendingNodeAngle)
    
    x = orbCoords.x*(Math::cos(periapsisArg)*Math::cos(ascendingNodeAngle) - Math::sin(periapsisArg)*Math::cos(inclination)*Math::sin(ascendingNodeAngle))
        - orbCoords.y*(Math::sin(periapsisArg)*Math::cos(ascendingNodeAngle) + Math::cos(periapsisArg)*Math::cos(inclination)*Math::sin(ascendingNodeAngle))
    y = orbCoords.x*(Math::cos(periapsisArg)*Math::sin(ascendingNodeAngle) + Math::sin(periapsisArg)*Math::cos(inclination)*Math::cos(ascendingNodeAngle))
        + orbCoords.y*(Math::cos(periapsisArg)* Math::cos(inclination)* Math::cos(ascendingNodeAngle) - Math::sin(periapsisArg)* Math::sin(ascendingNodeAngle))
    z = orbCoords.x*(Math::sin(periapsisArg)*Math::sin(inclination)) + orbCoords.y*(Math::cos(periapsisArg)*Math::sin(inclination))

    xSpeed = orbCoords.xSpeed*(Math::cos(periapsisArg)*Math::cos(ascendingNodeAngle) - Math::sin(periapsisArg)*Math::cos(inclination)*Math::sin(ascendingNodeAngle))
        - orbCoords.ySpeed*(Math::sin(periapsisArg)*Math::cos(ascendingNodeAngle) + Math::cos(periapsisArg)*Math::cos(inclination)*Math::sin(ascendingNodeAngle))
    ySpeed = orbCoords.xSpeed*(Math::cos(periapsisArg)*Math::sin(ascendingNodeAngle) + Math::sin(periapsisArg)*Math::cos(inclination)*Math::cos(ascendingNodeAngle))
        + orbCoords.ySpeed*(Math::cos(periapsisArg)* Math::cos(inclination)* Math::cos(ascendingNodeAngle) - Math::sin(periapsisArg)* Math::sin(ascendingNodeAngle))
    zSpeed = orbCoords.xSpeed*(Math::sin(periapsisArg)*Math::sin(inclination)) + orbCoords.ySpeed*(Math::cos(periapsisArg)*Math::sin(inclination))

    CartesianCoord.new(x,y,z,xSpeed,ySpeed,zSpeed)
end

class Coords
    attr_reader :type
    def initialize()
        @type = :undefined
    end

    def type
        @type
    end
end

class CartesianCoord < Coords
    attr_reader :x, :y, :z, :xSpeed, :ySpeed, :zSpeed
    def initialize(x,y,z,xSpeed, ySpeed, zSpeed)
        @type = :cartesian
        @x=x
        @y=y
        @z=z
        @xSpeed=xSpeed
        @ySpeed=ySpeed
        @zSpeed=zSpeed
    end
end

class KeplerCoord < Coords
    attr_reader :semiMajorAxis, :eccentricity, :inclination, :ascendingNodeAngle, :periapsisArg, :mainAnom
    
    def initialize(semiMajorAxis, eccentricity, inclination, ascendingNodeAngle, periapsisArg, mainAnom)
        @type = :keplerian
        @semiMajorAxis = semiMajorAxis
        @eccentricity = eccentricity
        @inclination = inclination
        @ascendingNodeAngle = ascendingNodeAngle 
        @periapsisArg = periapsisArg
        @mainAnom = mainAnom
    end

    def toCartesian(referentialBody)
        eccentricAnomaly = eccentricAnomaly(@mainAnom, @eccentricity)
        trueAnomaly = trueAnomaly(eccentricAnomaly,eccentricity)
        distanceFromCentralBody = distanceFromCentralBody(@eccentricity,@semiMajorAxis, eccentricAnomaly)
        orbitalCoords = orbitalFrameCartesianCoord(@semiMajorAxis, trueAnomaly, distanceFromCentralBody,mu(referentialBody),eccentricAnomaly)
        
        bodyCentricFrameCartesianCoord(orbitalCoords,@inclination,@periapsisArg,@ascendingNodeAngle)
    end
end