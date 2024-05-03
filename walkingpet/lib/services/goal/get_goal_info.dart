import 'package:http/http.dart' as http;
import 'dart:convert';

Future<Map<String, dynamic>> getGoalInfo() async {
  final response =
      await http.get(Uri.parse('https://walkingpet.co.kr/goal/info'));
  if (response.statusCode == 200) {
    //문자열 데이터 (response)를 JSON으로 파싱
    var jsonResponse = jsonDecode(response.body);
    print(jsonResponse['data']);
    return jsonResponse['data'];
  } else {
    print("목표 받아오기 에러남 : ${response.statusCode}");
    throw Error();
  }
}
