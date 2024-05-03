import 'dart:convert';

import 'package:http/http.dart' as http;

const String baseUrl = "https://walkingpet.co.kr";
const String characterInfo = "character?userCharacterId=22";

// 캐릭터 정보 조회
Future<Map<String, dynamic>> getCharacterInfo() async {
  final url = Uri.parse('$baseUrl/$characterInfo');
  final response = await http.get(url);

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
