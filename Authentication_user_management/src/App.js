import logo from './logo.svg';
import './App.css';
import {withAuthenticator, AmplifySignOut} from "@aws-amplify/ui-react";
import Securityquestions from './Securityquestions';
import firebase from './firebase';
import Auth from 'aws-amplify';
import {BrowserRouter, Route, Switch } from "react-router-dom";
import Restaurant from './pages/restaurant';
import Order from './pages/order';
import Dashboard from './pages/Dashboard';
import NamedEntities from './components/NamedEntity';

function App() {

  return (

        <Switch>
        <Route exact path="/">
          <Securityquestions/>
        </Route>
        <Route exact path="/restaurant">
          <Restaurant/>
        </Route>
        <Route exact path="/order">
          <Order/>
        </Route>
        <Route exact path="/dashboard">
          <Dashboard/>
        </Route>
        <Route exact path="/namedentity">
          <NamedEntities/>
        </Route>
      </Switch>
      
      
  );
}

export default withAuthenticator(App);

