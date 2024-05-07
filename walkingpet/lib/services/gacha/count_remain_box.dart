import 'package:http/http.dart' as http;
import 'dart:convert';

Future<Map<String, dynamic>> countRemainBox() async {
  final response =
      await http.get(Uri.parse('https://walkingpet.co.kr/gacha/count'));
  if (response.statusCode == 200) {
    print(response.body);
    //문자열을 Json으로 파싱
    var jsonData = jsonDecode(response.body);
    print(jsonData["data"]);

    return jsonData["data"];
  } else {
    print("박스 수 정보 받아오기 에러 : ${response.statusCode}");
    throw Error();
  }
}
