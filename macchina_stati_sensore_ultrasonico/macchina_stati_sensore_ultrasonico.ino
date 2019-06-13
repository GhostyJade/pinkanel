#include <HCSR04.h>

UltraSonicDistanceSensor sensor1(5, 6);
//UltraSonicDistanceSensor sensor2(toBeDet, toBeDet);

const int puntiPin = 10;
const int tempoPin = 9;
int statoPrecedente;
double tempoIn;
const int tempoPartita = 300000;
int distanzaIn1;
int distanzaIn2;
int punti1;
int punti2;
int stato = 0;

void setup() {
  Serial.begin(9600);
  pinMode(puntiPin,INPUT);
  pinMode(tempoPin,INPUT);
  distanzaIn1 = sensor1.measureDistanceCm();
  //distanzaIn2 = sensor2.measureDistanceCm();
}

int goal(){
   //due if per controllare in quale porta è stato segnato un gol e di conseguenza ++puntiX X = 1 || 2
       
}

void loop() {
  switch(stato){
    case 0:
      if(digitalRead(puntiPin) == HIGH) stato = 1;
      if(digitalRead(tempoPin) == HIGH) stato = 2;
      statoPrecedente = stato;
      tempoIn = millis();
      punti1 = 0;
      punti2 = 0;
      break;
    case 1:
      //partita a punti
      break;
    case 2:
      //partita a tempo
      if((millis()-tempoIn) < tempoPartita){
        goal();
      }
      else if((millis()-tempoIn) > tempoPartita){
        stato = 3;
      }
      break;
    case 3:
      //stato fine partita
      stato = 0;
  }
}
