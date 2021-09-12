import React from 'react';
import Button from 'react-bootstrap/Button';
import Card from "react-bootstrap/Card";
import restaurantImage from '../asset/restaurant_2.svg';
import firebase from "../firebase";

function RestaurantCard() {
    const ref = firebase.firestore().collection("restaurant");

    console.log(ref.get())

    return (
        <div class="card-group">
                <Card style={{ width: '18rem' }}>
                <Card.Img variant="top" src={restaurantImage} />
                <Card.Body>
                    <Card.Title>Punjabi Dhaba</Card.Title>
                    <Card.Text>
                        Rain or shine, it’s time to dine
                    </Card.Text>
                    <Button variant="primary">View Menu</Button>
                </Card.Body>
                </Card>
                <Card style={{ width: '18rem' }}>
                <Card.Img variant="top" src={restaurantImage} />
                <Card.Body>
                    <Card.Title>Swad</Card.Title>
                    <Card.Text>
                    We serve delious Gujarati Food.
                    </Card.Text>
                    <Button variant="primary">View Menu</Button>
                </Card.Body>
                </Card>
                <Card style={{ width: '18rem' }}>
                <Card.Img variant="top" src={restaurantImage} />
                <Card.Body>
                    <Card.Title>Garrison</Card.Title>
                    <Card.Text>
                    we serve burger and fries.
                    </Card.Text>
                    <Button variant="primary">View menu</Button>
                </Card.Body>
                </Card>
                
    </div>
    )
}

export default RestaurantCard;
