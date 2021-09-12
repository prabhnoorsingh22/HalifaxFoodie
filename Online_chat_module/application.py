from flask import Flask, render_template, request, redirect
from publisher import publisher_foodie
import subscriber as sub

application = app = Flask(__name__)


@application.route("/")
def index():
    return render_template("index.html")  # to send context to html


@application.route("/get", methods=['GET'])
def get_bot_response():

    userText = request.args.get("msg")
    userText2 = request.args.get("msg2")

    print(userText)
    print(userText2)

    if userText:

        publisher_foodie(userText)
        # sub.sub()
    else:
        sub.publisher_foodie(userText2)
    # return str(english_bot.get_response(userText))
    return "hello"


if __name__ == "__main__":
    application.run(host='0.0.0.0', port=8080, debug=True)
