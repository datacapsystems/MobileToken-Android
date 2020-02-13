# Getting started with MobileToken for Android

### Add DatacapMobileToken.aar to your Android (Gradle) project
1. Place DatacapMobileToken.aar in the `libs` folder of your app.
2. Ensure `libs` folder is included in `flatDir` repositories in your app's build.gradle file.

    ```
    repositories {
      flatDir {
        dirs 'libs'
      }
    }
    ```

3. Add DatacapMobileToken.aar to the `dependencies` in your app's build.gradle file.

    ```
    dependencies {
      compile (name: 'DatacapMobileToken', ext: 'aar')
    }
    ```

### Include the library in your code
```java
import datacap.mobiletoken.*;
```

### Implement tokenization result handler
##### Implement `onActivityResult` for `DatacapTokenizerActivity.DATACAP_TOKENIZER_REQUEST` requestCode
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data)
{
    if (requestCode == DatacapTokenizerActivity.DATACAP_TOKENIZER_REQUEST)
    {
        if (resultCode == DatacapTokenizerActivity.RESULT_SUCCESS)
        {
            // A token has been received!
            DatacapToken token = (DatacapToken)data.getSerializableExtra("token");
        }
        else if (resultCode == DatacapTokenizerActivity.RESULT_ERROR)
        {
            // A tokenization error has occurred!
            DatacapTokenizationError error = (DatacapTokenizationError)data.getSerializableExtra("error");
        }
        else if (resultCode == DatacapTokenizerActivity.RESULT_CANCELED)
        {
            // The user has cancelled tokenization!
        }
    }
}
```

For the `DatacapTokenizerActivity.RESULT_SUCCESS` resultCode, the received Intent contains a `DatacapToken` object named `"token"` that contains 5 String members:
* `token`: The one-time-use token for the user-entered account data.
* `brand`: The card brand of account represented by the token.
* `expirationMonth`: The 2-digit expiration month of the account.
* `expirationYear`: The 4-digit expiration year of the account.
* `last4`: The last 4 digits of the account number.

For the `DatacapTokenizerActivity.RESULT_ERROR` resultCode, the received Intent contains a `DatacapTokenizationError` object named `"error"` that contains 2 members:
* `errorCode`: The DatacapMobileToken error code.
* `errorMessage`: The error description.

The `DatacapTokenizationError` object's member `errorCode` is an enum of 4 possible values:
* `ErrorCodes.CONNECTION_ERROR`: Failed to communicate with Datacap Token API.
* `ErrorCodes.AUTHENTICATION_ERROR`: Public key authentication failed.
* `ErrorCodes.VALIDATION_ERROR`: Failed to tokenize due to invalid account information.
* `ErrorCodes.UNKNOWN_ERROR`: An error has occurred tokenizing the account data at the Datacap Token API.

### Request a token for keyed account
##### Create an Intent for `DatacapTokenizerActivity` and provide a Datacap public key as an extra named `"publicKey"` then start activity for result with the Intent
```java
Intent tokenIntent = new Intent(this, DatacapTokenizerActivity.class);
tokenIntent.putExtra("publicKey", "[Public Key Goes Here]");
//"environment" should only be supplied when targeting the certification environment. When targeting production do not pass an environment.
tokenIntent.putExtra("environment", "cert");
startActivityForResult(tokenIntent, DatacapTokenizerActivity.DATACAP_TOKENIZER_REQUEST);
```

### Report bugs
If you encounter any bugs or issues with the latest version of MobileToken for Android, please report them to us by opening a [GitHub Issue](https://github.com/datacapsystems/MobileToken-Android/issues)!
