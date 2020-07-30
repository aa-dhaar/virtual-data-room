package com.aa.virtualroom.validator;

import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonSchemaValidator {


	public static boolean validation(String jsonSchema) {
		JsonObject convertedObject = new Gson().fromJson(jsonSchema, JsonObject.class);
		if(convertedObject==null || convertedObject.isJsonNull()) {
			throw new UnsupportedOperationException("Json schema can't be blank or null");
		}
		if(convertedObject.has("$schema") && convertedObject.has("$id")) {
			if(!isVaild(convertedObject))
				throw new UnsupportedOperationException("The Jsonschema format is not valid");
		}
		return true; 
	}

	private static boolean isVaild(JsonObject convertedObject) {

		if(convertedObject.has("type")) {
			if(convertedObject.get("type").getAsString().equals("object") && convertedObject.has("additionalProperties")  && !convertedObject.get("additionalProperties").getAsBoolean() && convertedObject.has("properties")) {
				return isVaild(convertedObject.get("properties").getAsJsonObject());
			}
		}
		else {
			return validateTypeNotObject(convertedObject);
		}

		return false; 
	}

	private static boolean validateTypeNotObject(JsonObject convertedObject) {

		Set<String> listOfJson = convertedObject.getAsJsonObject().keySet();

		for (String keyElement : listOfJson) {
			JsonObject valueElement = convertedObject.getAsJsonObject().getAsJsonObject(keyElement);
			if(valueElement.has("type") && valueElement.get("type").getAsString().equals("object") && valueElement.has("additionalProperties") && !valueElement.get("additionalProperties").getAsBoolean() && valueElement.has("properties")) {
				return isVaild(convertedObject.get("properties").getAsJsonObject());
			}
			else if((valueElement.has("type") && valueElement.get("type").getAsString().equals("boolean")) || (valueElement.has("type") && valueElement.get("type").getAsString().equals("string") && valueElement.has("enum"))) {
				continue;
			}
			else
				return false;
		}

		return true;
	}

}
