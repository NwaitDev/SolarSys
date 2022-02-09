require 'uri'
require 'net/http'
require 'fileutils'
require 'json'


OpenSSL::SSL::VERIFY_PEER = OpenSSL::SSL::VERIFY_NONE


def open_url(url)
    Net::HTTP.get(URI.parse(url))
end


data = open_url("https://api.le-systeme-solaire.net/rest/bodies")

bodies = ["mercure", "venus","terre", "mars", "jupiter", "saturne", "uranus", "neptune" , "pluton"]

bodies_data = {}
bodies.each do |body|
    bodies_data[body] =  { "data" => JSON.parse(open_url("https://api.le-systeme-solaire.net/rest/bodies/" + body)) , "moons" => [] }
end


for body in bodies
    FileUtils.mkdir_p "../bodies/" + body + "/moon"
end


parsed_data = JSON.parse(data)


for line_data in parsed_data["bodies"]
  #  p line_data["id"]
    if line_data["aroundPlanet"] != nil
        if  bodies.include? line_data["aroundPlanet"]["planet"]
            bodies_data[line_data["aroundPlanet"]["planet"]]["moons"].push(line_data)
        end 
    end
end

for body in bodies
    File.open("../bodies/" + body + "/" + body + ".json", "w") do |f|
        f.write(JSON.pretty_generate(bodies_data[body]["data"]))
    end
    for moon in bodies_data[body]["moons"]
        File.open("../bodies/" + body + "/moon/" + moon["name"].gsub(" ", "_").gsub("/", "_BS_")   + ".json", "w") do |f|
            f.write(JSON.pretty_generate(moon))
        end
    end 
end




#p parsed_data