using UnityEngine;
using UnityEngine.UI;

public class PrebidInitializer : MonoBehaviour
{
    private const string JavaClassName = "com.example.prebidunitysdk.EventManagementUnity";

    public Text debugText;
    private AndroidJavaObject unityActivity;
    void Start()
    {
        //ShowToast("Unity to Android Bridge Initialized");
    }

    public void StartApp(string msg) {
        if (Application.platform == RuntimePlatform.Android) {
            AndroidJavaClass unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            unityActivity = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");
            AndroidJavaObject alert = new AndroidJavaObject(JavaClassName);
            alert.Call("PrintString", unityActivity, msg);
        }
    }

    public void LoadAds(string msg) {
        if (Application.platform == RuntimePlatform.Android) {
            AndroidJavaClass unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            unityActivity = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");
            AndroidJavaObject alert = new AndroidJavaObject(JavaClassName);
            alert.Call("createAd", unityActivity);
        }
    }

    public void ShowAds() {
        if (Application.platform == RuntimePlatform.Android) {
            if (Application.platform == RuntimePlatform.Android) {
                //AndroidJavaClass unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
                //AndroidJavaObject unityActivity = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");
                AndroidJavaObject alert = new AndroidJavaObject(JavaClassName);
                //alert.Call("HideAdView", unityActivity);
                alert.Call("ShowAdView", unityActivity);
            }
            
        }
    }

    public void HideAds() {
        if (Application.platform == RuntimePlatform.Android) {
            //AndroidJavaClass unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            //AndroidJavaObject unityActivity = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");
            AndroidJavaObject alert = new AndroidJavaObject(JavaClassName);
            alert.Call("HideAdView", unityActivity);
        }
    }

    private void AppendDebugText(string message)
    {
        if (debugText != null)
        {
            debugText.text += message + "\n";
        }
    }
}