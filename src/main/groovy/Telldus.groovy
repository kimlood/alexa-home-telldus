import com.amazonaws.services.lambda.runtime.Context; 
import static java.util.UUID.randomUUID
import org.scribe.builder.*
import org.scribe.builder.api.*
import org.scribe.model.*
import org.scribe.oauth.*
import groovy.json.JsonSlurper
import groovy.json.*

//Change this to own 

public class Telldus{
	
	
	public static void main(String[] args) {
		def telldus = new Telldus()
		telldus.handler([header:[messageId:'6d6d6e14-8aee-473e-8c24-0d31ff9c17a2', name:'TurnOnRequest', namespace:'Alexa.ConnectedHome.Control', payloadVersion:'2'], payload:[accessToken:'Atza|5A', appliance:[applianceId:1068213, additionalApplianceDetails:[extraDetail3:'but they should only be used for reference purposes.', extraDetail4:'This is not a suitable place to maintain current device state', extraDetail1:'optionalDetailForSkillAdapterToReferenceThisDevice, extraDetail2:There can be multiple entries']]]], null)
		
	}
	def handler(def event, Context context){
		println "INFO: " + event 
		if (event.header.namespace == 'Alexa.ConnectedHome.Discovery') {
			println "INFO: Alexa.ConnectedHome.Discovery - Sending response" 
			return getDevicesRequest()
		} else if (event.header.namespace == 'Alexa.ConnectedHome.Control') {
			println "INFO: Alexa.ConnectedHome.Control"
			def deviceId = event.payload.appliance.applianceId
			def messageId = event.header.messageId
			
			if (event.header.name == 'TurnOnRequest'){
				println "INFO: Handle TurnOnRequest"
				return doRequest(deviceId, "turnOn", messageId, "TurnOnConfirmation")
			
			} else if (event.header.name== 'TurnOffRequest') {
				println "INFO: Handle TurnOffRequest"
				return doRequest(deviceId, "turnOff", messageId, "TurnOffConfirmation")
			}
		}

	}
	
	def messageId () {
		return randomUUID() as String
	}
	
	def doRequest(def id, def type, def messageId, def returnName) {
		Map<String, String> params = new HashMap<>()
		params.put("id", id)
		
		println "INFO: id " + id
		OAuthRequest request = createAndSignRequest("device/" + type, params)
		println "INFO: Sending request"
		Response response = request.send()
		
		JsonSlurper jsonSlurper = new JsonSlurper()
        def json = new JsonSlurper().parseText(response.getBody())
		def reply = [:]
		println "INFO: Telldus reply: " + json 
		
		if (json.status == 'success') {
			reply['header'] = ["namespace": 'Alexa.ConnectedHome.Control', "name": returnName, "messageId": messageId, "payloadVersion": '2']
		} else {
			println "ERROR: Handle error?"
		}
		
			reply['payload'] = [:]
		
		println "INFO: Control reply " + reply 
		return reply 
	}
	
	def getDevicesRequest() { 
	    def reply=[:]
		println "INFO: getDevicesRequest"
			
		OAuthRequest request = createAndSignRequest("devices/list", null)
		Response response = request.send()
        def json = new JsonSlurper().parseText(response.getBody())
		
		reply['header'] = ["namespace": 'Alexa.ConnectedHome.Discovery', "name": 'DiscoverAppliancesResponse', "messageId": messageId(), "payloadVersion": '2']
			
		def getAllDevices = []
		json.device.each{ telldus->
			 getAllDevices.add(["applianceId":telldus."id",
                    "manufacturerName":"Telldus",
                    "modelName":"Unkown",
                    "version":"1",
                    "friendlyName":telldus."name",
                    "friendlyDescription":"Telldus - " + telldus."name",
                    "isReachable":true,
                    "actions":[
                        "turnOn",
                        "turnOff"
                    ],
                    "additionalApplianceDetails":[
						 "extraDetail1":"optionalDetailForSkillAdapterToReferenceThisDevice",
                        "extraDetail2":"There can be multiple entries",
                        "extraDetail3":"but they should only be used for reference purposes.",
                        "extraDetail4":"This is not a suitable place to maintain current device state"
					]])	
			
				
		} 
		reply['payload'] = ["discoveredAppliances":getAllDevices]
	
		println "INFO: Device request reply: " + reply
		return reply
		
	}
	
	def telldusUrl(String extension) {
		return "http://api.telldus.com/json/" + extension
	}

	def OAuthRequest createAndSignRequest(String url, Map<String, String> parameters) {
		println "INFO: createAndSignRequest url: " + url  + " Parameters: " + parameters
		OAuthRequest request = createRequest(url, parameters)
		OAuthService oService = createAuthService()
		Token accessToken = createAccessToken()
		
		oService.signRequest(accessToken, request)
		
		return request
	}

	def Token createAccessToken() {
		Global global = new Global(); 
		String publicToken = global.getPublictoken()
		String secretToken = global.getSecrettoken()

		Token accessToken = new Token(publicToken, secretToken)
		
		return accessToken
	}

	def OAuthService createAuthService() {
		Global global = new Global(); 
		String publicKey = global.getPublickey()
		String secretKey = global.getSecretkey()

		OAuthService oService = new ServiceBuilder().provider(GoogleApi).apiKey(publicKey).apiSecret(secretKey).build()
		return oService
	}

	def OAuthRequest createRequest(String url, Map<String, String> parameters) {
		// Create, sign and send request.
		OAuthRequest request = new OAuthRequest(Verb.GET, telldusUrl(url))

		if (parameters != null) {
			for (String parameterName : parameters.keySet()) {
				String parameterValue = parameters.get(parameterName)

				request.addQuerystringParameter(parameterName, parameterValue)
			}
		}

		return request
	}
	
}



