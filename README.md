# InstallKit
Install kit is just an app installer but I expect you could learn useful something from this project if you are a beginner.

app installer တခုသာ ဖြစ်ပေမဲ့ အခြေခံရှိသူတွေအတွက်  ပိုပြီး smart ကျကျ code ရေးနိုင်အောင် လေ့လာနိုင်မဲ့ project တခုဖြစ်ဖို့ မျှော်လင့်ပါတယ် 

#### မှတ်ချက်
> အကြံပေးချက်တွေက ကျွန်တော့် လက်ရှိအတွေ့အကြုံအရ သာဖြစ်ပြီး starter တွေအတွက်သာ ဖြစ်ပါတယ်
> ဒီ project မှာ `android 10` နဲ့ အထက်အတွက် `SAF` ကို မသုံးဘဲ legacy External Storage attribute ကိုသာ သုံးထားလို့ အမြင့်ဆုံး `android 10` အထိသာ  အလုပ်လုပ်ပါမယ် ၊ ပိုရှုပ်မှာစိုးလို့ မထည့်တော့တာပါ
> AIDE `3.2.190430` ထိ အဆင်ပြေအောင် androidx library `1.0.2` ကိုသာ သုံးပေးထားပါတယ်

project တွင် လေ့လာရန်

1. Model Class
2. View Model
3. Mutable Live Data
4. Runtime permission requests
5. AsyncTask, UI Thread & background operations
6. WeakReference and memory leak
7. recursive method
8. custom Adapter
9. custom dialog
10. File Provider
11. read info of external apk file
12. catch all possible user actions


### 1. Model Class

Pojo လို့လည်း သိကြပါတယ် ၊ object တွေ အများကြီးကို object တစ်ခုတည်းဖြစ်အောင် ရေးတာပါ

ဥပမာ - app name, version, file size, file path စတဲ့ data (object) တွေ အများကြီးကို တခုချင်းစီ list လုပ်မဲ့အစား object တခုတည်းမှာပဲ ပေါင်းရေးပြီး List ထဲထည့်ရေးလေ့ရှိပါတယ် ၊ List ယူဖို့အတွက်သာ မဟုတ်ဘဲ တခြားနေရာတွေမှာလည်း object တွေကို တစ်စုတစ်စည်းတည်း ယူချင် ၊ ပို့ချင်တဲ့အခါမှာလည်း သုံးနိုင်တယ်
 
data တွေကို ပြန်ယူသုံးတဲ့အခါ 
```java
model.getPath(), model.getName()
```
စသဖြင့် အလွယ်ခေါ်သုံးနိုင်တယ်

model class တခုမှာ အများအားဖြင့် သုံးမဲ့ objects (String , Integer, Boolean etc ..) တွေ ၊ Constructor ၊ getter နဲ့ setter methods တွေပဲ ပါ, ပါတယ်

getter တွေကို သက်ဆိုင်ရာ object reference ကို ပြန်ယူဖို့အတွက်သာ သုံးတယ် 
ဥပမာ - 
```java
public int getVersion() { return version; }
```
public variables တွေကို တိုက်ရိုက် ခေါ်သုံးနိုင်ပေမဲ့ getter method နဲ့ပဲ ခေါ်လေ့ရှိပါတယ် 

Constructor ကတော့ data (objects) တွေ လက်ခံဖို့ အတွက်ပါ 
 ဥပမာ - 
 ```java
 public MyModel (int count) { }
 ```
constructor က ဝင်လာတဲ့ data ကို model class ထဲမှာပဲ variable ဆောက်ပြီး သိမ်းတယ်
ဥပမာ - 
```java
this.count = count;
```
setter method ကတော့ အသုံးနည်းပါတယ် ၊ constructor ရဲ့ parameter တခုခုကနေ သိမ်းလိုက်တဲ့ initial value ကို ပြင်ချင်တဲ့အခါမျိုး ၊ constructor နဲ့ လက်ခံမထားတဲ့ data ထည့်ရာမျိုးမှာ သုံးတယ်

Model class တွေကို Activity တခုကနေ တခုဆီ Intent နဲ့ ပို့ချင်ရင် `Parcelable` or `Serializable` - interface တခုခုကို implement လုပ်ရပါတယ်


### 2. View Model

ဒါကိုတော့ fragment to fragment, fragment to activity, activity to fragment စသဖြင့် အချင်းချင်း data အပြန်အလှန် ပို့ရာ ၊
UI နဲ့ သက်ဆိုင်တဲ့ data တွေ သိမ်းရာမှာ သုံးလေ့ရှိတယ် 

အခု pj မှာတော့ AppLoader class ကနေ UI ကို data ပို့ရာမှာ သုံးထားပါတယ် 

data ပြောင်းလဲမှုတွေကို အပို ဘာမှထပ်ရေးစရာ မလိုဘဲ Live သိရအောင် `MutableLiveData` နဲ့ တွဲသုံးလေ့ရှိတယ်

ViewModel ထဲက data တွေဟာ screen rotation စတဲ့ configuration changes ဖြစ်လည်း မပျက်ပါ
configuration changes ကြောင့် data တွေ မပျက်အောင် `onSaveInstanceState()` နဲ့ သိမ်းနိုင်ပေမဲ့ serializable data နည်းနည်းသာ သိမ်းနိုင်ပါတယ်


### 3. Mutable Live Data

  data ပြောင်းလဲမှုတွေကို Live သိရအောင် `MutableLiveData` ကို သုံးနိုင်ပါတယ် 
  data ထည့်ဖို့ `setValue()` method ကို သုံးပြီး UI thread မဟုတ်တဲ့ တခြား thread ကနေ ထည့်ဖို့ `postValue()` method ကို သုံးရပါတယ်

data ယူဖို့ `getValue()` method ကို သုံးနိုင်ပြီး `observe()` method နဲ့ live data ယူနိုင်တယ်


### 4. runtime permission requests

android 6.0+ မှာ တချို့ permission တွေက manifest မှာ ရေးရုံနဲ့ မရဘဲ user ခွင့်ပြုချက် လိုပါတယ် ၊ အခု pj မှာ `READ_EXTERNAL_STORAGE` permission ကို အလွယ် တောင်းပြထားပါတယ်


### 5. AsyncTask, UI Thread & background operations

AsyncTask က `android 11` မှာ deprecated ဖြစ်သွားပါပြီ ၊ နောက် project မှ AsyncTask မသုံးဘဲ ရေးပြပါမယ်

file listing, create, internet က data ယူတာ စတာတွေက အချိန်အတော်ကြာနိုင်ပြီး heavy task တွေ ဖြစ်ပါတယ် ၊ UI thread မှာ ရေးခဲ့ရင် app က freeze ဖြစ်ပြီး အဲ့အောက်မှာ ရေးခဲ့တဲ့ code တွေက heavy task တွေ ပြီးမှသာ အလုပ်လုပ်နိုင်ပါလိမ့်မယ်

ဒါကြောင့် heavy task တွေကို new thread တခုမှာ အလုပ်လုပ်စေရပါတယ် ၊ ဒါကို background operations လို့လည်း မှတ်နိုင်ပါတယ် 

asynctask ကလည်း new thread မှာ အလုပ်လုပ်ပြီး အပိုင်း 3 ပိုင်းရှိတယ် 
`onPreExecute ()` - background task အလုပ်မလုပ်မီမှာ loading circle ပြတာ စသဖြင့် background thread စတော့မှာဖြစ်ကြောင်း user ကို အသိပေးနိုင်ပါတယ်
`doInBackground()` - heavy task တွေကို ဒီထဲမှာ စပြီး အလုပ်လုပ်စေနိုင်ပါတယ်
`onPostExecute()` - heavy task တွေ ပြီးသွားတဲ့အခါ ဒီ method ဆီ ရောက်တယ်၊ UI မှာ download complete, scanning complete စသဖြင့် ပြနိုင်ပါတယ်


### 6. Weak Reference and memory leak

weak reference ကို သုံးတာက good approach တော့ မဟုတ်ပါဘူး ၊ ဒါမဲ့ memory leak မဖြစ်စေဖို့ သုံးနိုင်ပါတယ်

app က object  တွေ ဆောက်တဲ့အခါ ART (android runtime environment) က garbage collection (GC) ကို အလုပ်လုပ်စေပါတယ် ၊ GC က မသုံးတော့တဲ့ object တွေကို memory ထဲကနေ အလိုအလျောက် ရှင်းပေးပါတယ်

Asynctask ကို activity ရဲ့ non-static inner class အနေနဲ့ ရေးတယ် ၊ ဒါမှမဟုတ် `Context` or `Activity` လိုအပ်လို့ constructor or method  နဲ့ reference ယူသုံးတယ်ဆိုပါစို့ 

ပြီးတော့ Activity က screen rotate .. စတဲ့ configurations changes ကြောင့် activity အသစ်ပြန်ဆောက် (activity recreate) လိုက်တယ် ဆိုပါစို့

activity recreate လိုက်ပေမဲ့ စောစောက reference ယူသုံးထားတဲ့ context တွေနဲ့ (အဲ့ context သုံးပြီး inflate လုပ်ထားတဲ့ View တွေ ရှိရင်လည်း အဲ့ view တွေ အပါအဝင်) က memory ထဲမှာ ကျန်နေခဲ့မှာဖြစ်ပါတယ် ၊ ဒါတွေကို garbage collection က ရှင်းမပေးနိုင်ဘူး
context reference ကို သိမ်းထားတဲ့အတွက် memory leak ဖြစ်တယ် 

မဖြစ်အောင် `WeakReference <Context>` နဲ့ သုံးနိုင်ပါတယ်၊ ဥပမာ - context ပို့ရင်
```java
wrContext = new WeakReference<> (context)); 
```
context ယူသုံးရင် -
```java
context = wrContext.get();
```

### 7. recursive method

အသုံးများပါတယ်၊ method တခုထဲမှာ ဒီ method ကိုပဲ ထပ်ခေါ်သုံးတာကို recursive method လို့ ခေါ်ပါတယ် ၊ ဥပမာ
```java
void listFiles (File path) {
     if ( path . isDirectory ()) {
        listFiles (path);
     } else {
     addFile (path);
     }
}
```
အပေါ်က sample code မှာ path က ဖိုင်ဖြစ်ရင် list ထဲ မှတ်ထားပြီး 
folder တခုဖြစ်မယ်ဆိုရင် အဲ့ folder ထဲက ဖိုင်တွေကိုပါ ထပ်ပြီး list ထုတ်လိုက်ပါတယ် ၊ ဒါဟာ တကြိမ်သာ ဖြစ်တာမဟုတ်ပဲ ရှိသမျှ sub folder တွေ အားလုံး မကုန်မချင်း အလုပ်လုပ်နေမှာ ဖြစ်ပါတယ်
```java
listFiles (parent_folder);
```
လို့ ခေါ်သုံးလိုက်ရုံနဲ့ parent folder နဲ့ sub folder တွေထဲက ဖိုင်အားလုံးကို method တခုတည်းနဲ့ ရလာပါမယ်


### 8. custom Adapter

ဒီမှာက ပြောချင်တာ အများကြီးမရှိပါဘူး ၊ `ListView` အတွက်  custom adapter ရေးမယ်ဆိုရင် `getView()` method မှာ 
`LayoutInflater` နဲ့ view တခု inflate လုပ်ရပါတယ်၊ အဲ့ view ကို တခါတည်း return ပြန်ပေးမဲ့အစား `null` ဖြစ်မဖြစ် စစ်ပြီးမှ ပြန်သင့်ပါတယ်၊ တကယ်လို့ `null` မဖြစ်ခဲ့ရင် view တခုထပ်ဆောက်နေစရာ မလိုတော့တာကြောင့် app performance ပိုကောင်းပါတယ် 

အခု pj က demo မို့ lib များမှာစိုးလို့ `ListView` နဲ့ ရေးလိုက်တာပါ ၊ `RecyclerView` သုံးတာ ပိုကောင်းပါတယ်


### 9. custom dialog

Progress Dialog က `api 26` က စပြီး deprecated ဖြစ်သွားတဲ့အတွက် ပုံစံတူတဲ့ custom progress dialog တခု အလွယ်ရေးပြထားပါတယ်

ProgressDialog က user အတွက် app သုံးဖို့ကို ကာကွယ်ထားသလို ဖြစ်တာကြောင့် deprecated ဖြစ်သွားတယ်လို့ သိရပါတယ် 

### 10. File Provider

target SDK က နောက်ဆုံး version ကို ထား, ထားတယ်ဆိုရင် `API 24` နဲ့ အထက်မှာ ကိုယ့် app က ဖိုင်တခုခုကို အပြင်ကို share ပေး ၊ ဒါမှမဟုတ် အပြင်က app နဲ့ ဖွင့်ချင်တယ် ၊ app install ဖို့ ဖိုင်ကို package installer ဆီ ပို့ချင်တယ်ဆိုရင် `FileProvider` ကို သုံးရပါတယ် 

manifest မှာ provider tag ထည့်ပေးရတယ်၊ provider name က ကိုယ်သုံးတဲ့ library ပေါ် မူတည်ပြီး ကွဲပါမယ်
ခု pj မှာ androidx lib ကို သုံးထားလို့ `androidx.core.content.FileProvider` လို့ ရေးထားတာပါ
/res/xml/ ထဲမှာ paths.xml တခုဆောက်ပြီး provider => metadata မှာ path ထည့်ပါ


### 11. read info of external apk file

`PackageInfo` class ကိုသုံးပြီး app icon, name, version etc ယူနိုင်ပါတယ် 
```java
if (context == null) return;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkPath, 0);

        if (packageInfo != null) {
            packageInfo.applicationInfo.sourceDir = apkPath;
            packageInfo.applicationInfo.publicSourceDir = apkPath;

            Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);
            String appName = (String) packageInfo.applicationInfo.loadLabel(packageManager);
            String appVersion = packageInfo.versionName;
        }
```

### 12. catch all possible user actions

ကိုယ်က ဒီလိုသုံးဖို့ ရေးထားပေမဲ့ user တွေက နောက်တမျိုး ပြောင်းသုံးတတ်ကြပါတယ် 
ဖြစ်နိုင်တဲ့ user အသုံးပြုပုံတွေကို handle လုပ်မထားဘူးဆိုရင် app crash တာ ၊ ကိုယ် မဖြစ်စေချင်တာတွေ ဖြစ်တတ်ပါတယ်

screen rotation, config changes စတဲ့ possible changes တွေကလည်း ကိုယ့် code အလုပ်လုပ်ပုံကို အကျိုးသက်ရောက်စေလား စစ်သင့်ပါတယ်

အရှင်းဆုံး ဥပမာ ပြရရင် -
Login function တခုမှာ user က Login ခလုတ်ကို နှိပ်တဲ့အခါ username နဲ့ password field ဖြည့်ပြီးမှသာ အလုပ်လုပ်စေတာမျိုး ပါ

အခု app မှာ listview ဟာ အပေါ်ဆုံး item ကို ရောက်နေမှသာ Swipe refresh ကို ဖွင့်ပေးထားပါတယ်၊

ဒီလိုလုပ်မထားဘူးဆိုရင် list အောက်ခြေကို ရောက်နေချိန် အပေါ်က item တွေကို ပြန်ကြည့်ဖို့ user က ဆဆွဲကြည့်တဲ့အခါ swipe က အလုပ်လုပ်ပြီး အပေါ်ထိ တင်မရဘဲ ဖြစ်ပါလိမ့်မယ်

ဒါကြောင့် ဖြစ်နိုင်တဲ့ user actions တွေကို ထည့်စဉ်းစားဖို့ လိုပါတယ် 

****************************************

   #### bug နည်းပြီး update ပေးရလွယ်တဲ့ app ကောင်းတစ်ခု ရဖို့ အောက်ကအချက်တွေကို ကြိုးစားကြည့်ပါ

- မျိုးတူရာ class တွေကို package folder တစ်ခုစီ ခွဲသိမ်းပါ ၊ အမြင်ရှင်းပြီး ရှာရလွယ်ပါတယ် 

- `deprecated classes & methods` တွေကို အတတ်နိုင်ဆုံး ရှောင်ပါ ၊ app compatibility ကို ဦးစားပေးပါ

- method name, class name တွေကို သူတို့ရဲ့ လုပ်ဆောင်မှု နဲ့ ထပ်တူကျအောင် ပေးပါ ၊ method name ရဲ့ အစ စကားလုံးတိုင်းကို small letter နဲ့ ရေးပါ

-  `isFileApk()` , `installApk()` စတဲ့ UI ကို တိုက်ရိုက်မပြောင်းလဲစေတဲ့ methods ၊ ဘုံ methods တွေကို JcUtils class စတဲ့ class တွေ ဆောက်ပြီး ခွဲရေးပါ။ Activity ထဲမှာ code တွေဟာ ရှင်းလင်းပြီး နားလည်လွယ်ရပါမယ်

- UI Thread မှာ heavy task တွေ run တာကို ရှောင်ပါ

- uncaught exception တွေ မရှိအောင် ဂရုစိုက်ပါ

- မလိုအပ်ဘဲ UI တွေ သုံးတာကို ရှောင်ပါ ၊ UX ကို ဦးစားပေးပါ
ဥပမာ - background running task မရှိဘဲ loading dialog ပြတာ ၊ background running task မရှိဘဲ splash screen မှာ progress ထည့်တာ 

- clean UI ဖြစ်အောင် ရေးပါ

- possible user actions တွေကို ထည့်စဉ်းစားပါ
