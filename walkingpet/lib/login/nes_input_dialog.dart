import 'package:flutter/material.dart';
import 'package:nes_ui/nes_ui.dart';

/// {@template nes_input_dialog}
/// A dialog that shows a message requiring a value to
/// input from the user.
///
/// For ease when showing a dialog, use [NesInputDialog.show] method.
/// {@endtemplate}
class CustomNesInputDialog extends StatefulWidget {
  /// {@macro nes_input_dialog}
  const CustomNesInputDialog({
    required this.inputLabel,
    required this.message,
    super.key,
  });

  /// Value of the input label.
  final String inputLabel;

  /// Value of the cancel label.

  /// The inputation message.
  final String message;

  /// A shortcut method that can be used to show this dialog.
  ///
  /// Defaults:
  /// - [inputLabel] Ok
  /// - [cancelLabel] Cancel
  static Future<String?> show({
    required BuildContext context,
    required String message,
    String inputLabel = 'Ok',
    String cancelLabel = 'Cancel',
    NesDialogFrame frame = const NesBasicDialogFrame(),
  }) {
    return NesDialog.show<String?>(
      context: context,
      builder: (_) => CustomNesInputDialog(
        inputLabel: inputLabel,
        message: message,
      ),
      frame: frame,
    );
  }

  @override
  State<CustomNesInputDialog> createState() => _CustomNesInputDialogState();
}

class _CustomNesInputDialogState extends State<CustomNesInputDialog> {
  late final TextEditingController _controller = TextEditingController();

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Text(widget.message),
        const SizedBox(height: 16),
        TextField(
          controller: _controller,
          textAlign: TextAlign.center,
          autofocus: true,
          //style: const TextStyle(fontSize: 20),
        ),
        const SizedBox(height: 16),
        Row(
          mainAxisSize: MainAxisSize.min,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            NesButton(
              type: NesButtonType.primary,
              child: Text(
                widget.inputLabel,
                style: const TextStyle(color: Colors.white),
              ),
              onPressed: () {
                Navigator.of(context).pop(_controller.text);
              },
            ),
          ],
        ),
      ],
    );
  }
}
