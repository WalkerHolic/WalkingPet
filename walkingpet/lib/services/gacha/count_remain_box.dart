import 'package:http/http.dart' as http;
import 'dart:convert';
import '../Interceptor.dart';

Future<Map<String, dynamic>> countRemainBox() async {
  final client = AuthInterceptor();
  final response =
      await client.get(Uri.parse('https://walkingpet.co.kr/gacha/count'));
  if (response.statusCode == 200) {
    //문자열을 Json으로 파싱
    var jsonData = jsonDecode(response.body);

    return jsonData["data"];
  } else {
    throw Error();
  }
}
