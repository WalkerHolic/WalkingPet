import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

void showRecord(BuildContext context) {
  showDialog(
    context: context,
    barrierDismissible: true,
    builder: (BuildContext context) {
      return Center(
        child: Material(
          color: Colors.transparent,
          child: Image.asset('assets/images/diary.png'),
        ),
      );
    },
  );
}
