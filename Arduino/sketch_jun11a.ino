#include <Ultrasonic.h>

/*
 * Pass as a parameter the trigger and echo pin, respectively,
 * or only the signal pin (for sensors 3 pins), like:
  Ultrasonic ultrasonic(13); 
 ////////////////////////you may need to switch the two numbers below////////////////////////
 */
Ultrasonic ultrasonic1(8);
Ultrasonic ultrasonic2(7);

int distanza = 100;

String g1 = "Giocatore 1";
String g2 = "Giocatore 2";
int point1 = 0;
int point2 = 0;
bool men = false;

void setup() {
  Serial.begin(9600);
}

void loop() {
  if (!men){
    menu();
    men = true;
  }
}

void menu(){
  while(true){
    Serial.println("Scegli come giocare: ");
    Serial.println("1) A punti");
    Serial.println("2) A tempo");
    break;
  }
  punti();
   
}

void  punti(){
  if (point1>10){
    vittoria(g1);
    return;
  }
  else if(point2>10){
    vittoria(g2);
    return;
  }
  Goal();
  punti();
}

void Goal(){
  if (ultrasonic1.read()<distanza){
    Serial.print("Goal giocatore 1");
    point1++;
  }
  else if (ultrasonic2.read()<distanza){
    Serial.print("Goal giocatore 2");
    point2++;
  }
  Serial.print(point1+"a"+point2);
  delay(1000);
}

void vittoria(String g){
  Serial.print("HA VINTO "+ g);
}
