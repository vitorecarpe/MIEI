import mysql.connector
import requests
import json
import threading
from time import sleep
from flask import Flask
from flask import render_template

mydb = mysql.connector.connect(
  host="localhost",
  user="BD_sensores",
  passwd="",
  database="sensores_card"
)

app = Flask(__name__)

@app.route('/')
def index():
   return render_template('index.html')

@app.route('/patient')
def patient():
    mycursor = mydb.cursor()

    mycursor.execute(
                "select * from patient"
            )
    data = mycursor.fetchall()
    return render_template('patient.html', data = data)

@app.route('/sensor')
def sensor():
    mycursor = mydb.cursor()

    mycursor.execute(
                "select * from sensor"
            )
    data = mycursor.fetchall()
    return render_template('sensor.html', data = data)

@app.route('/register')
def register():
    mycursor = mydb.cursor()

    mycursor.execute(
                "select * from register"
            )
    data = mycursor.fetchall()
    return render_template('register.html', data = data)

def getInfo():
    while True:

        response1 = requests.get("http://nosql.hpeixoto.me/api/sensor/3001")
        response2 = requests.get("http://nosql.hpeixoto.me/api/sensor/3002")
        response3 = requests.get("http://nosql.hpeixoto.me/api/sensor/3003")
        response4 = requests.get("http://nosql.hpeixoto.me/api/sensor/3004")
        response5 = requests.get("http://nosql.hpeixoto.me/api/sensor/3005")

        responses = []
        responses.append(response1)
        responses.append(response2)
        responses.append(response3)
        responses.append(response4)
        responses.append(response5)

        for response in responses:
            data = json.loads(response.text)

            for i in data:
                if i == "sensorid":
                    sensorid = data[i]
                
                if i == "sensornum":
                    sensornum = data[i]
                
                if i == "type_of_sensor":
                    type_of_sensor = data[i]
                
                if i == "patient":
                    for j in data[i]:
                        if j == "patientid":
                            patientid = data[i][j]
                        if j == "patientname":
                            patientname = data[i][j]
                        if j == "patientbirthdate":
                            patientbirthdate = data[i][j]
                        if j == "patientage":
                            patientage = data[i][j]
                
                if i == "bodytemp":
                    bodytemp = data[i]
                
                if i == "bloodpress":
                    for j in data[i]:
                        if j == "systolic":
                            systolic = data[i][j]
                        if j == "diastolic":
                            diastolic = data[i][j]
                
                if i == "bpm":
                    bpm = data[i]
                
                if i == "timestamp":
                    timestamp = data[i]

            mycursor = mydb.cursor()

            mycursor.execute(
                "select * from patient WHERE idpatient = %s",
                (patientid,)
            )
            mycursor.fetchall()
            # gets the number of rows affected by the command executed
            row_count = mycursor.rowcount

            if row_count == 0:
                sql = "insert into patient (idpatient, name, birthday_date, age) values (%s, %s, %s, %s)"
                val = (patientid, patientname, patientbirthdate, patientage)
                mycursor.execute(sql, val)
                mydb.commit()

            mycursor.execute(
                "select * from sensor WHERE idsensor = %s",
                (sensorid,)
            )
            mycursor.fetchall()
            # gets the number of rows affected by the command executed
            row_count = mycursor.rowcount

            if row_count == 0:
                sql = "insert into sensor (idsensor, number, type, patient_idpatient) values (%s, %s, %s, %s)"
                val = (sensorid, sensornum, type_of_sensor, patientid)
                mycursor.execute(sql, val)
                mydb.commit()


            sql = "insert into register (bodytemp, bloodpress_sys, bloodpress_dias, bpm, timestamp, sensor_idsensor) values (%s, %s, %s, %s, %s, %s)"
            val = (bodytemp, systolic, diastolic, bpm, timestamp, sensorid)
            mycursor.execute(sql, val)
            mydb.commit()

        # definição do intervalo entre pedidos
        sleep(5)

t1 = threading.Thread(target=getInfo)
t1.start()

if __name__ == '__main__':
   app.run(debug = True)