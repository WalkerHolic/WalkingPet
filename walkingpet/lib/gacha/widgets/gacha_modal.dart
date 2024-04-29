import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class GachaModal extends StatelessWidget {
  const GachaModal({super.key});

  @override
  Widget build(BuildContext context) {
    return Dialog(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20),
      ),
      elevation: 16, //모달창이 3D로 떠있는 효과. 숫자가 커질수록 두드러짐
      child: const SizedBox(
        height: 400,
        width: 300,
      ),
    );
  }
}
