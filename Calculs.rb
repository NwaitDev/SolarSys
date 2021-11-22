#EPOCH = 1 jan 2000
def absoluteDayFromEpoch(y,m,d, ut=0)
    367*y - 7 * ( y + (m+9)/12 ) / 4 - 3 * ( ( y + (m-9)/7 ) / 100 + 1 ) / 4 + 275*m/9 + d - 730515 + ut/24
end

class CartPosition
    attr_reader :x, :y, :z
    def initialize(x,y,z)
        @x = x
        @y=y
        @z=z
    end
end

class OrbitalElement
    attr_reader :ellipse, :longAscNode, :inclin, :perihelArg, :meanAnom

    def initialize(ellipse, longAscNode, inclin, perihelArg, meanAnom)
        @ellipse = ellipse
        @longAscNode = longAscNode
        @inclin = inclin
        @perihelArg = perihelArg
        @meanAnom = meanAnom
    end

end


class Ellipse

    attr_reader :centerX, :centerY, :semiminorAxis, :semimajorAxis, :eccentricity, :semifociDist

    def initialize(semiminorAxis, semimajorAxis, centerX=0, centerY=0)
        @semiminorAxis = semimajorAxis
        @semimajorAxis = semimajorAxis
        @eccentricity = Math.sqrt(1-(semiminorAxis/semimajorAxis)**2)
        @semifociDist = semimajorAxis*@eccentricity
        @centerX = centerX
        @centerY = centerY
    end

end

class Trajectory
    attr_reader :orbitElem, :perihelLong, :meanLong, :perihelDist, :aphelDist, :orbitalPeriod,
    :perihelTime, :currentPos,
    #Next needs the current position to be calculated
    :trueAnom, #angle between the foci where the ReferenceCelestialBody is, the perihelion position and the actual position of the celestialBody
    :eccentricAnom #https://en.wikipedia.org/wiki/Eccentric_anomaly
    def initialize(orbitElem, perihelTime, currentPos)
        @orbitElem = orbitElem
        @perihelLong = orbitElem.longAscNode + orbitElem.perihelArg
        @meanLong = orbitElem.perihelArg+orbitElem.meanAnom
        @perihelDist = orbitElem.ellipse.semimajorAxis*(1-orbitElem.ellipse.eccentricity)
        @aphelDist = orbitElem.ellipse.semimajorAxis*(1+orbitElem.ellipse.eccentricity)
        @orbitalPeriod = orbitElem.ellipse.semimajorAxis ** (1.5)
        @perihelTime = perihelTime
        @trueAnom = :noCalculationYet
        @eccentricAnom = :noCalculationYet
    end
    
end

p x = Trajectory.new(OrbitalElement.new(Ellipse.new(1,1,0,0),3,0,0,1),absoluteDayFromEpoch(2000,1,3),CartPosition.new(1,0,-1))