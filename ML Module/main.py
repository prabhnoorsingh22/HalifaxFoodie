from google.cloud import automl

def automl1(request):
    project_id = "database-assignment-2-317714"
    model_id = "TCN4368938040270258176"
    content = "Cheese Pizza needs dough and cheese topping"

    json_object = request.get_json()

    prediction_client = automl.PredictionServiceClient()


    model_full_id = automl.AutoMlClient.model_path(project_id, "us-central1", model_id)

    if request.args and 'recipe' in request.args:
        content = request.args.get('recipe')
    else:
        text_snippet = automl.TextSnippet(content=content, mime_type="text/plain")
        payload = automl.ExamplePayload(text_snippet=text_snippet)
        response = prediction_client.predict(name=model_full_id, payload=payload)
        for annotation_payload in response.payload:
            return str(annotation_payload.display_name + ',' + str(annotation_payload.classification.score))

    text_snippet = automl.TextSnippet(content=content, mime_type="text/plain")
    payload = automl.ExamplePayload(text_snippet=text_snippet)
    response = prediction_client.predict(name=model_full_id, payload=payload)
    for annotation_payload in response.payload:
        str1="Name of the similar recipe is="+annotation_payload.display_name+" and the prediction score is="+ str(annotation_payload.classification.score)
        return str1