using UnityEngine;

public class AdManager : MonoBehaviour
{
    private AndroidJavaObject javaObject;

    void Start()
    {
        // Get the current activity context from Unity
        using (AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
        {
            AndroidJavaObject currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
            javaObject = new AndroidJavaObject("com.example.prebidunitysdk.EventManagementUnity", currentActivity);
        }
    }

    public void HideAd()
    {
        if (javaObject != null)
        {
            javaObject.Call("HideAdView");
        }
        else
        {
            Debug.LogError("Java object is null, cannot call HideAdView");
        }
    }
}