import 'package:flutter/material.dart';
import 'package:nes_ui/nes_ui.dart';

class CustomNesInputDialog extends StatefulWidget {
  final String inputLabel;
  final String message;

  const CustomNesInputDialog({
    super.key,
    required this.inputLabel,
    required this.message,
  });

  static Future<String?> show({
    required BuildContext context,
    required String message,
    String inputLabel = 'Ok',
  }) {
    return showDialog<String?>(
      context: context,
      builder: (context) => CustomNesInputDialog(
        inputLabel: inputLabel,
        message: message,
      ),
    );
  }

  @override
  _CustomNesInputDialogState createState() => _CustomNesInputDialogState();
}

class _CustomNesInputDialogState extends State<CustomNesInputDialog> {
  final TextEditingController _controller = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  String? _errorMessage;

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  bool _validateInput(String value) {
    if (value.isEmpty) {
      setState(() {
        _errorMessage = '닉네임을 입력해주세요.';
      });
      return false;
    }
    RegExp regex = RegExp(r'^[가-힣A-Za-z0-9]{2,6}$');
    if (!regex.hasMatch(value)) {
      setState(() {
        _errorMessage = '한글, 영어, 숫자만 가능합니다.';
      });
      return false;
    }
    setState(() {
      _errorMessage = null;
    });
    return true;
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(widget.message),
          const SizedBox(height: 16),
          TextFormField(
            controller: _controller,
            textAlign: TextAlign.center,
            autofocus: true,
            validator: (value) {
              if (value == null || !_validateInput(value)) {
                return ' '; // This space ' ' is used to activate the error message space.
              }
              return null;
            },
          ),
          if (_errorMessage != null)
            Text(
              _errorMessage!,
              style: const TextStyle(color: Colors.red, fontSize: 12),
            ),
          const SizedBox(height: 12),
          NesButton(
            type: NesButtonType.primary,
            child: Text(widget.inputLabel,
                style: const TextStyle(color: Colors.white)),
            onPressed: () {
              if (_formKey.currentState!.validate()) {
                Navigator.of(context).pop(_controller.text);
              }
            },
          ),
        ],
      ),
    );
  }
}
