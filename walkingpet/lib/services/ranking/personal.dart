import 'package:http/http.dart' as http;
import 'dart:convert';

const String baseUrl = "https://walkingpet.co.kr";
const String yesterdayTop10 = "ranking/person/top10?value=yesterday";

Future<Map<String, dynamic>> getTop10() async {
  final url = Uri.parse('$baseUrl/$yesterdayTop10');
  final response = await http.get(url);

  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    var jsonData = jsonDecode(data);
    print(jsonData);
    return jsonData;
  } else {
    print("개인랭킹 어제자 Top10: ${response.statusCode}");
    throw Error();
  }
}
