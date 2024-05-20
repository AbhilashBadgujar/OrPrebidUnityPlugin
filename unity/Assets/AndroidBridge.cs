using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AndroidBridge : MonoBehaviour
{
    private AndroidJavaObject _activity;

    void Start()
    {
        using (var unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
        {
            _activity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
        }
    }

    public void ShowLayout()
    {
        if (Application.platform == RuntimePlatform.Android) {
            AndroidJavaClass unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            AndroidJavaObject ua = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");
            AndroidJavaObject alert = new AndroidJavaObject("com.example.showhide.MainActivity");
            
            alert.Call("ShowAdView");
        }
    }

    public void HideLayout()
    {
        if (Application.platform == RuntimePlatform.Android) {
            AndroidJavaClass unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            AndroidJavaObject ua = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");
            AndroidJavaObject alert = new AndroidJavaObject("com.example.showhide.MainActivity");
            
            alert.Call("hideLayout");
        }
    }
}
