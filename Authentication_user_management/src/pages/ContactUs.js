import React from 'react';
import { AmplifyChatbot } from "@aws-amplify/ui-react"

function ContactUs() {
    return (
        <div>
            <h1>
                Contact Us
            </h1>
            <AmplifyChatbot botName="BookTrip_dev" />
        </div>
    )
}

export default ContactUs
