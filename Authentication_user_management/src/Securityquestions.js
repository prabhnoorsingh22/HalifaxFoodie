import { AmplifySignOut } from '@aws-amplify/ui-react'
import React, {useState,useEffect} from 'react'
import { useHistory } from "react-router-dom";
import Amplify, { Auth } from 'aws-amplify';
import firebase from "./firebase";
import awsconfig from './aws-exports';
import Button from 'react-bootstrap/Button';
import Card from "react-bootstrap/Card";
import RestaurantCard from './components/restaurantCard';
import Navbar from './components/navbar';
import NavigationBar from './components/navbar';
import { AmplifyChatbot } from "@aws-amplify/ui-react"
import axios from 'axios';
import WordCloud from './components/wordCloud';
import MachineLearning from './components/machinelearning';
import NamedEntities from './components/NamedEntity';
Amplify.configure(awsconfig);

export default function Securityquestions() {

    const ref = firebase.firestore().collection("user");
    console.log(ref);
    const history = useHistory();
    const [success, setSuccess] = useState(false);
    const [answer, setAnswer] = useState("");
    const [role, setRole] = useState("");
    const question = 'What is your favorite color?';

    const routeChange=()=> {
        history.push("/dashboard");
    }

    const namedEnitity = ()=> {
        history.push("/namedentity");
    }
    const handleSubmit = async e => {
        e.preventDefault();
        const user = {
            username: Auth.user.username,
            email: Auth.user.attributes.email,
            phone: Auth.user.attributes.phone_number,
            securityQuestion: question,
            securityAnswer: answer,
            role: role
        }

        ref
        .doc(Auth.user.username)
        .get()
        .then((doc) => {
          if (doc.exists) {
            var ans= doc.data().securityAnswer;
            if (ans === user.securityAnswer) {
              setSuccess(true)
            } else {
                alert('Incorrect Security Answer')
            }
          } else {
                ref
                    .doc(Auth.user.username)
                    .set(user)
                    .then(() => {
                        console.log('Firebase User operation success')
                    })
                    .catch((err) => {
                        console.error('Error in addUserToFirestore: ' + err)
                    })
                    console.log(user);
                    setSuccess(true)
            }
        })
        .catch((error) => {
          console.log('Error getting document:', error)
        })
    }
    
    
    

    return (
        <div className="row">
            { success ?
                
                <div>
                    <RestaurantCard/>
                    <AmplifyChatbot botName="HalifaxFoodie"/>
                    <MachineLearning/>
                    <Button variant="primary" onClick={routeChange}>
                     Dashboard
                    </Button>
                    <Button variant="primary" onClick={namedEnitity}>
                     Word Cloud
                    </Button>
                    <AmplifySignOut/>
                </div>
                :
                <div className="login-wrapper">
                <h1>Security Question</h1>
                <form  onSubmit={handleSubmit}>
                    <label>
                    <p>What is your favorite color?</p>
                    <input type="text" value={answer} onChange={(e) => setAnswer(e.target.value)} />
                    </label>
                    <label>
                    <p>What is your role?</p>
                    <input type="text" value={role} onChange={(e) => setRole(e.target.value)} />
                    </label>
                    <div>
                    <button type="submit">Submit</button>
                    </div>
                </form>
                </div> }
        </div>
    )
}
