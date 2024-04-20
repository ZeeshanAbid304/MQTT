package com.flimngo.mqtt

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var mqttHandler: MqttHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mqttHandler = MqttHandler()

        findViewById<Button>(R.id.connectButton).setOnClickListener {
            connectToBroker()
        }

        findViewById<Button>(R.id.subscribeButton).setOnClickListener {
            subscribeToTopic()
        }

        findViewById<Button>(R.id.publishButton).setOnClickListener {
            publishMessage()
        }
    }

    override fun onDestroy() {
        mqttHandler!!.disconnect()
        super.onDestroy()
    }

    private fun connectToBroker() {
        val brokerUrl = "tcp://test.mosquitto.org:1883"
        val clientId = "android_${System.currentTimeMillis()}_${(0..9999).random()}"// Replace with your desired client ID
        mqttHandler!!.connect(brokerUrl, clientId)
    }

    private fun publishMessage() {
        val topic = findViewById<EditText>(R.id.topicEditText).text.toString()
        val message = findViewById<EditText>(R.id.messageEditText).text.toString()
        Toast.makeText(this, "Publishing message: $message", Toast.LENGTH_SHORT).show()
        mqttHandler!!.publish(topic, message)
    }

    private fun subscribeToTopic() {
        val topic = findViewById<EditText>(R.id.topicEditText).text.toString()
        Toast.makeText(this, "Subscribing to topic $topic", Toast.LENGTH_SHORT).show()
        mqttHandler!!.subscribe(topic)
    }
}