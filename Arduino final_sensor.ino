#include <hcsr04.h>

const int echoPin1 = 6;
const int echoPin2 = 3;
const int triggerPin1 = 5;
const int triggerPin2 = 11;

HCSR04 sensor1(triggerPin1, echoPin1, 20, 4500);
HCSR04 sensor2(triggerPin2, echoPin2, 20, 4500);

const String g1 = "Giocatore 1";
const String g2 = "Giocatore 2";

const int ballDiameter = 30;

int point1;
int point2;
int initialDistance1;
int initialDistance2;
int distance1;
int distance2;

void reset();
void checkOne();
void checkTwo();
void addPoint(String g);

/**
 * This function is used to set up variables with an initial value, in this case we also initialize two objects
 */
void setup() {
  Serial.begin(9600);
  point1 = 0;
  point2 = 0;
  initialDistance1 = sensor1.distanceInMillimeters();
  initialDistance2 = sensor2.distanceInMillimeters();
}

/**
 * This function is used to call all the function of our arduino project and is continuously repeated
 */
void loop() {
  checkOne();
  checkTwo();
  reset();
}

/**
 * This function checks if the ball crossed the path of the first HCSR04 ultrasonic sensor and calls the function addPoint() as a result
 */
void checkOne(){
    distance1 = sensor1.distanceInMillimeters();
    if (distance1 < (initialDistance1 - ballDiameter)) {
    addPoint(g1);
    Serial.print(1);
    Serial.print(point1);
    Serial.println();
    delay(1500);
  }
}

/**
 * This function checks if the ball crossed the path of the second HCSR04 ultrasonic sensor and calls the function addPoint() as a result
 */
void checkTwo(){
    distance2 = sensor2.distanceInMillimeters();
    if (distance2 < (initialDistance2 - ballDiameter)) {
    addPoint(g2);
    Serial.print(2);
    Serial.print(point2);
    Serial.println();
    delay(1500);
  }
}

/**
 * This function resets the score by putting both point variables to zero after one of the two reached 10
 */
void reset(){
  if(point1 > 10 || point2 > 10){
    point1 = 0;
    point2 = 0;
    delay(5000);
  }
}

/**
 * This function increases the values by one of the point<?> depending on the content of the string g which needs to be passed as a parameter 
 */
void addPoint(String g){
    g.equals(g1) ? point1++ : point2++;
}
