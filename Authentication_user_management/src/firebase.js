import firebase from "firebase/app";
import "firebase/firestore";
import "firebase/auth";

const firebaseConfig = {
    apiKey: "AIzaSyC-gFNExxdf5FQTahlnuNxPO5JnG2t7pIc",
    authDomain: "csci5410serverless-315116.firebaseapp.com",
    projectId: "csci5410serverless-315116",
    storageBucket: "csci5410serverless-315116.appspot.com",
    messagingSenderId: "55257739142",
    appId: "1:55257739142:web:b809afcc387e91d4fac607",
    measurementId: "G-3GXW0QXWC9"
};

firebase.initializeApp(firebaseConfig);

export default firebase;