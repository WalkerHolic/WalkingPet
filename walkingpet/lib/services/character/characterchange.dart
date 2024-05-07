import 'dart:convert';

import 'package:http/http.dart' as http;

import '../Interceptor.dart';

const String baseUrl = "https://walkingpet.co.kr";
const String characterInfo = "character/list";

// 캐릭터 변경 모달
Future<Map<String, dynamic>> getCharacterChange() async {
  final client = AuthInterceptor();
  final url = Uri.parse('$baseUrl/$characterInfo');
  final response = await client.get(url);

  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    var jsonData = jsonDecode(data);
    print(jsonData);
    return jsonData;
  } else {
    print("캐릭터 정보 받아오기 오류 : ${response.statusCode}");
    throw Error();
  }
}
