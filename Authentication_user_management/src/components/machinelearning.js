import React from "react";
import ReactWordCloud from "react-wordcloud";
import axios from 'axios';



const MachineLearning = React.memo((props) => {
  console.log("output", props.wordCloud);
  return (
    <div className="word-cloud-report">
      <h1>Machine Learning</h1>
      <textarea rows="10" cols="50" placeholder="Enter the recipe" id="input_recipe"></textarea>
      <button onClick={getRecommendation} float="right">See recommendation</button>


      </div>
      
  );
  
});

const getRecommendation = async () => {
    var textInput = document.getElementById("input_recipe").value;
    console.log("Entering");
    let dish = {"recipe":textInput}
    const {data} = await axios.post("https://us-central1-database-assignment-2-317714.cloudfunctions.net/anammikaserverlessproject", );
    var recommendation = data.split(",")[0];
    var score = data.split(",")[1];
    window.alert("The recommended item is "+recommendation+" and the similarity score is "+score)
}

export default MachineLearning;