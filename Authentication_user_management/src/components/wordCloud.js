import React from "react";
import ReactWordCloud from "react-wordcloud";

const WordCloud = React.memo((props) => {
  console.log("output", props.wordCloud);
  return (
    <div className="word-cloud-report">
      <h1>Word Cloud</h1>
      <ReactWordCloud
        words={props.wordCloud}
        options={{
          colors: [
            "#1f77b4",
            "#ff7f0e",
            "#2ca02c",
            "#d62728",
            "#9467bd",
            "#8c564b",
          ],
          // enableTooltip: true,
          // deterministic: false,
          fontFamily: "impact",
          fontSizes: [10, 30],
          // fontSizes: [20, 60],
          // fontStyle: "normal",
          fontWeight: "bold",
          // padding: 1,
          rotations: 3,
          rotationAngles: [0, 90],
          scale: "sqrt",
          spiral: "archimedean",
          // transitionDuration: 1000,
        }}
      />
    </div>
  );
});

export default WordCloud;