require 'uri'
require 'net/http'
require 'fileutils'
require 'json'


def open_url(url)
    Net::HTTP.get(URI.parse(url))
end
  
uri = URI('https://api.le-systeme-solaire.net/rest/bodies/jupiter')
res = Net::HTTP.get_response(uri)

bodies = ["mercure", "venus","terre", "mars", "jupiter", "saturne", "uranus", "neptune" , "pluton"]

for body in bodies
    FileUtils.mkdir_p "bodies/" + body + "/moon"
end

for body in bodies
    result = open_url("https://api.le-systeme-solaire.net/rest/bodies/" + body)
    result = JSON.parse(result)
    File.open("bodies/" + body + "/" + body + ".json" , 'w') { |file| file.write(JSON.generate(result)) }
    moons = result['moons']
    p result['englishName']
    if moons 
        moons.each do |moon|
        if moon["moon"] != "S/2011 J 1" && moon["moon"] != "S/2004 S 27" && moon["moon"] != "S/2004 S 29"
            p moon["moon"]
            result_moon = open_url(moon['rel'])
            result_moon = JSON.parse(result_moon)
            File.open("bodies/" + body +  "/moon/" + moon["moon"].gsub(" ", "_").gsub("/", "_BS_")  + ".json" , 'w') { |file| file.write(JSON.generate(result_moon)) }
        end
        end
    end

end