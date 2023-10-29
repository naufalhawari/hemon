package com.example.hemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hemon.databinding.ActivityAsesmenStrokeBinding;


import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import ai.onnxruntime.OrtSession.Result;

public class AsesmenStrokeActivity extends AppCompatActivity {

    ActivityAsesmenStrokeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAsesmenStrokeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Load the ONNX model from the assets directory
        String modelPath = "file:///android_asset/model.onnx";
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        OrtSession session = env.createSession(modelPath, new OrtSession.SessionOptions());

        // Prepare input data as a float array
        float[] inputData = { ... }; // Your input data

        // Create an ONNX tensor from the input data
        OnnxTensor inputTensor = OnnxTensor.createTensor(env, inputData);

        // Make predictions
        Result output = session.run(new OrtSession.Input[]{new OrtSession.Input("input", inputTensor)});
        float[] outputData = output.get(0).getValueAsFloatArray();


    }

//    private submit(){
//
//    }
//
//    private clear(){
//
//    }
}