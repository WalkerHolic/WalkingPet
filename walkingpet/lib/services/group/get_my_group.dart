import 'package:http/http.dart' as http;
import 'dart:convert';
import '../Interceptor.dart'; // JSON 데이터를 다루고, 문자열 인코딩 및 디코딩을 위함

Future<List<Map<String, dynamic>>> getMyGroup() async {
  final response =
      await http.get(Uri.parse('https://walkingpet.co.kr/team/belong'));
  if (response.statusCode == 200) {
    //  응답 바이트를 UTF-8로 디코딩 (디코팅 안하면 한글이 깨져서 온다)
    var data = utf8.decode(response.bodyBytes);
    // 디코딩된 문자열을  Json으로 파싱
    var jsonData = jsonDecode(data);
    print(jsonData['data']);
    return (jsonData['data'] as List).map((item) {
      return Map<String, dynamic>.from(item);
    }).toList();
  } else {
    //상태 코드와 함께 에러 메세지 출력력
    print("에러남 : ${response.statusCode}");
    throw Error();
  }
}
