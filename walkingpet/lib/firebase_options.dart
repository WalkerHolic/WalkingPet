// File generated by FlutterFire CLI.
// ignore_for_file: type=lint
import 'package:firebase_core/firebase_core.dart' show FirebaseOptions;
import 'package:flutter/foundation.dart'
    show defaultTargetPlatform, kIsWeb, TargetPlatform;

/// Default [FirebaseOptions] for use with your Firebase apps.
///
/// Example:
/// ```dart
/// import 'firebase_options.dart';
/// // ...
/// await Firebase.initializeApp(
///   options: DefaultFirebaseOptions.currentPlatform,
/// );
/// ```
class DefaultFirebaseOptions {
  static FirebaseOptions get currentPlatform {
    if (kIsWeb) {
      return web;
    }
    switch (defaultTargetPlatform) {
      case TargetPlatform.android:
        return android;
      case TargetPlatform.iOS:
        return ios;
      case TargetPlatform.macOS:
        return macos;
      case TargetPlatform.windows:
        return windows;
      case TargetPlatform.linux:
        throw UnsupportedError(
          'DefaultFirebaseOptions have not been configured for linux - '
          'you can reconfigure this by running the FlutterFire CLI again.',
        );
      default:
        throw UnsupportedError(
          'DefaultFirebaseOptions are not supported for this platform.',
        );
    }
  }

  static const FirebaseOptions web = FirebaseOptions(
    apiKey: 'AIzaSyAq3OcgpR_Hvc8p794U9XjGkkRpfq-kj1I',
    appId: '1:2904722847:web:12c7ff709d23f4e8cf01aa',
    messagingSenderId: '2904722847',
    projectId: 'walkingpet-60412',
    authDomain: 'walkingpet-60412.firebaseapp.com',
    storageBucket: 'walkingpet-60412.appspot.com',
    measurementId: 'G-YPDFZ18P4P',
  );

  static const FirebaseOptions android = FirebaseOptions(
    apiKey: 'AIzaSyAsZWDSEVWbpjMwT9NRkx_37nxnHPGyYIw',
    appId: '1:2904722847:android:21d00c49768f228acf01aa',
    messagingSenderId: '2904722847',
    projectId: 'walkingpet-60412',
    storageBucket: 'walkingpet-60412.appspot.com',
  );

  static const FirebaseOptions ios = FirebaseOptions(
    apiKey: 'AIzaSyCIBb_nOj7191MA-519VN_YFEwFsroMS0M',
    appId: '1:2904722847:ios:e49567ec39caa855cf01aa',
    messagingSenderId: '2904722847',
    projectId: 'walkingpet-60412',
    storageBucket: 'walkingpet-60412.appspot.com',
    iosBundleId: 'com.walkerholic.walkingpet',
  );

  static const FirebaseOptions macos = FirebaseOptions(
    apiKey: 'AIzaSyCIBb_nOj7191MA-519VN_YFEwFsroMS0M',
    appId: '1:2904722847:ios:e49567ec39caa855cf01aa',
    messagingSenderId: '2904722847',
    projectId: 'walkingpet-60412',
    storageBucket: 'walkingpet-60412.appspot.com',
    iosBundleId: 'com.walkerholic.walkingpet',
  );

  static const FirebaseOptions windows = FirebaseOptions(
    apiKey: 'AIzaSyAq3OcgpR_Hvc8p794U9XjGkkRpfq-kj1I',
    appId: '1:2904722847:web:8123f1e5b639c4d5cf01aa',
    messagingSenderId: '2904722847',
    projectId: 'walkingpet-60412',
    authDomain: 'walkingpet-60412.firebaseapp.com',
    storageBucket: 'walkingpet-60412.appspot.com',
    measurementId: 'G-Y35JBREGH3',
  );
}