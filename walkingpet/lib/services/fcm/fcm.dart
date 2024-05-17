import 'package:http/http.dart' as http;
import 'dart:convert';

Future<void> sendTokenToServer(String token) async {
  // 서버 URL 설정
  const String serverUrl = 'https://walkingpet.co.kr/fcm/saveToken';

  // HTTP 요청 코드
  try {
    final response = await http.post(
      Uri.parse(serverUrl),
      headers: {
        'Content-Type': 'application/json',
      },
      body: jsonEncode(<String, String>{
        'fcm_token': token,
      }),
    );

    if (response.statusCode == 200) {
      // 서버로부터의 응답 처리
      print('Token sent successfully: ${response.body}');
    } else {
      print('Failed to send token. Status code: ${response.statusCode}');
    }
  } catch (e) {
    print('Error sending token: $e');
  }
}
