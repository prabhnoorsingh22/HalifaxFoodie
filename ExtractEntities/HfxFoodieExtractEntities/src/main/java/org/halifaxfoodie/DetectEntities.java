package org.halifaxfoodie;

import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.model.*;

import java.util.List;

public class DetectEntities {

    public String detectEntity(AmazonComprehend comprehend, String review) {
        String entity = "";
        try {
            DetectKeyPhrasesRequest request = new DetectKeyPhrasesRequest()
                .withText(review).withLanguageCode("en");
            DetectKeyPhrasesResult result = comprehend.detectKeyPhrases(request);
            List<KeyPhrase> keyPhrases = result.getKeyPhrases();
            for (KeyPhrase keyPhrase : keyPhrases) {
                entity = keyPhrase.getText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}
