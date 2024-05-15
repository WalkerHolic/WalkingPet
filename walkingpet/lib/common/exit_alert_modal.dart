// alert_utils.dart
import 'package:flutter/material.dart';
import 'dart:io';

import 'package:flutter/services.dart';

class ExitModal extends StatelessWidget {
  const ExitModal({super.key});

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('종료'),
      content: const Text('종료 하시겠습니까?'),
      actions: <Widget>[
        TextButton(
          onPressed: () {
            Navigator.of(context).pop(false); // 취소
          },
          child: const Text('취소'),
        ),
        TextButton(
          onPressed: () {
            Navigator.of(context).pop(true); // 확인
          },
          child: const Text('종료'),
        ),
      ],
    );
  }
}

Future<void> showExitModal(BuildContext context) async {
  bool? result = await showDialog(
    context: context,
    builder: (BuildContext context) {
      return const ExitModal();
    },
  );

  if (result == true) {
    // 확인을 눌렀을 때 앱을 종료
    if (Platform.isAndroid) {
      SystemNavigator.pop();
    } else if (Platform.isIOS) {
      exit(0);
    }
  }
}
