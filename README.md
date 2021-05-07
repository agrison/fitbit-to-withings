# Import your Fitbit weight data into Withings Health Mate

So my Fitbit Aria just died, and I ordered a Withings Body+. 

Sadly it seems that you can only import steps from a Fitbit account, 
it appears that weight is not a valid use case. 

So I'm here, writing this small program to do it for me.

## Building & running

You can either just clone the repository and run the program with the path to your **MyFitbitData.zip** file.
Or you can go also build an image with graal native.

See `build.sh`

The program targets Java 8.

## Output

This program just extracts your daily weight data from your Fitbit exported zip file, and chunks them into files of 299 entries
as expected by the Withings web site.

You can just upload them one by one and you are good to go.

I did it only for weight data, the process is similar for heart rate, but it would generate too much file.

# Procedure

1. Login to your Fitbit Account
2. Go to **Settings > Data Export**, then ask for a full export
3. You will receive an email with a link that you need to click for confirmation
4. Wait for the website to generate a zip, it can take some time (around 30 minutes for me)
5. Download the **MyFitbitData.zip** archive
6. Either download the **fitbit-to-withings.jar** file from the [release page](https://github.com/agrison/fitbit-to-withings/releases/), and run it
 ```shell
java -jar fitbit-to-withings.jar /path/to/MyFitbitData.zip

Loaded 592 entries.
Created C:\dev\github\fitbit-to-withings\weight_1.csv
Created C:\dev\github\fitbit-to-withings\weight_2.csv
```  

   Or, run build the program from sources, and run it
```shell
./gradlew build
java -jar build/libs/fitbit-to-withings-1.0-all.jar /path/to/MyFitbitData.zip

Loaded 592 entries.
Created C:\dev\github\fitbit-to-withings\weight_1.csv
Created C:\dev\github\fitbit-to-withings\weight_2.csv
```

7. Go to your Withings app ( https://healthmate.withings.com/ )
8. Click on your name (upper right) > Settings > Your initials (AG, for me) > Import my data
9. Select the file **weight_1.csv** then click **Validate**
10. Reiterate step 9 as much as you have files

And you're done.

